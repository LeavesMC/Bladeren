package top.leavesmc.Bladeren.leaves;

import io.netty.buffer.Unpooled;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;
import net.minecraft.resources.ResourceLocation;
import top.leavesmc.Bladeren.ModInfo;
import top.leavesmc.Bladeren.event.DisconnectEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class LeavesProtocol {

    public static final ResourceLocation HELLO_ID = ModInfo.id("hello");
    public static final ResourceLocation FEATURE_MODIFY_ID = ModInfo.id("feature_modify");

    private static final Map<ResourceLocation, BiConsumer<LocalPlayer, FriendlyByteBuf>> dataHandlers = new HashMap<>();
    private static final Map<String, BiConsumer<LocalPlayer, CompoundTag>> featureHandlers = new HashMap<>();
    private static final Map<String, CompoundTag> featureBackData = new HashMap<>();
    private static final Map<String, String> featureData = new HashMap<>();
    private static final List<Runnable> helloEndTasks = new ArrayList<>();

    private static LocalPlayer localPlayer = null;
    @Getter
    private static boolean isLeavesServer = false;

    public static void init() {
        DisconnectEvent.register(LeavesProtocol::onDisconnect);
        dataHandlers.put(HELLO_ID, LeavesProtocol::handleHello);
    }

    public static void handleHello(LocalPlayer player, FriendlyByteBuf data) {
        String serverVersion = data.readUtf(64);
        CompoundTag tag = data.readNbt();

        isLeavesServer = true;

        if (tag != null) {
            CompoundTag featureNbt = tag.getCompound("Features");
            for (String name : featureNbt.getAllKeys()) {
                if (featureHandlers.containsKey(name)) {
                    featureHandlers.get(name).accept(localPlayer, featureNbt.getCompound(name));
                }
                featureData.put(name, featureNbt.getCompound(name).getString("Value"));
            }
        }

        FriendlyByteBuf backData = new FriendlyByteBuf(Unpooled.buffer());
        backData.writeUtf(ModInfo.MOD_VERSION);

        CompoundTag backTag = new CompoundTag();

        CompoundTag backFeatureNbt = new CompoundTag();
        for (String name : featureBackData.keySet()) {
            backFeatureNbt.put(name, featureBackData.get(name));
        }
        featureBackData.clear();
        backTag.put("Features", backFeatureNbt);

        backData.writeNbt(backTag);

        helloEndTasks.forEach(Runnable::run);

        sendPacket(HELLO_ID, backData);
    }

    public static void onPayload(LeavesPayload payload) {
        if (dataHandlers.containsKey(payload.id())) {
            dataHandlers.get(payload.id()).accept(localPlayer, payload.byteBuf());
        }
    }

    public static String getFeatureData(String name) {
        return featureData.getOrDefault(name, "null");
    }

    public static boolean isFeatureEnableOrLocal(String name) {
        return Minecraft.getInstance().isLocalServer() || isFeatureEnable(name);
    }

    public static boolean isFeatureEnable(String name) {
        return isLeavesServer && featureData.getOrDefault(name, "false").equals("true");
    }

    public static void registerDataHandler(ResourceLocation id, BiConsumer<LocalPlayer, FriendlyByteBuf> handler) {
        dataHandlers.put(id, handler);
    }

    public static void registerFeatureHandler(String id, BiConsumer<LocalPlayer, CompoundTag> handler) {
        featureHandlers.put(id, handler);
    }

    public static void registerHelloEndTask(Runnable task) {
        helloEndTasks.add(task);
    }

    public static void addFeatureBackData(String id, CompoundTag tag) {
        featureBackData.put(id, tag);
    }

    public static LocalPlayer getPlayer() {
        return localPlayer;
    }

    public static void sendPacket(ResourceLocation id, FriendlyByteBuf data) {
        localPlayer.connection.send(new ServerboundCustomPayloadPacket(new LeavesPayload(id, data)));
    }

    public static void sendFeatureModify(String name, CompoundTag tag) {
        sendPacket(FEATURE_MODIFY_ID, new FriendlyByteBuf(Unpooled.buffer()).writeUtf(name).writeNbt(tag));
    }

    public static void gameJoined(LocalPlayer player) {
        localPlayer = player;
    }

    private static void onDisconnect() {
        localPlayer = null;
        isLeavesServer = false;
        featureData.clear();
    }
}
