package mod.vadim212.onkey_command_mod;

import mod.vadim212.onkey_command_mod.commands.ModCommands;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.server.command.ConfigCommand;
import net.minecraftforge.server.command.ForgeCommand;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

@Mod("onkey_command_mod")
public class OnKeyCommandMod
{
    private final ModCommands regCommandClass;

    public OnKeyCommandMod() {
        MinecraftForge.EVENT_BUS.register(this);
        regCommandClass = new ModCommands();
    }

    @SubscribeEvent
    public void onCommandsRegister(RegisterCommandsEvent event) // onCommandsRegister
    {
        new ForgeCommand(event.getDispatcher());
        ConfigCommand.register(event.getDispatcher());
        regCommandClass.register(event.getDispatcher());
    }

    @SubscribeEvent(receiveCanceled = true)
    public void onKeyInput(InputEvent.KeyInputEvent e) {
        if (Minecraft.getInstance().player != null) {
            if (regCommandClass.getSettedKey() != -1) {
                if (e.getKey() == regCommandClass.getSettedKey() && e.getAction() == GLFW_PRESS) {

                    if (regCommandClass.getSettedCommand() != null) {
                        Minecraft.getInstance().player.sendChatMessage(regCommandClass.getSettedCommand());
                    } else {
                        Minecraft.getInstance().player.getCommandSource().sendErrorMessage(new TranslationTextComponent("commands.reg_command.no_reg"));
                    }
                }
            }
        }
    }

}
