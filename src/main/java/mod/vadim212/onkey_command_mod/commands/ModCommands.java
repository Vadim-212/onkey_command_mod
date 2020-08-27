package mod.vadim212.onkey_command_mod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.world.NoteBlockEvent;
import org.lwjgl.glfw.GLFW;

public class ModCommands {
    private int settedKey = -1;
    private String settedCommand = null;

    public void register(CommandDispatcher<CommandSource> dispatcher) {
        //dispatcher.register(Commands.literal("reg_command").redirect(cmd));
        //dispatcher.register(Commands.literal("reg_command").redirect(cmd2));
        dispatcher.register(
                Commands.literal("reg_command")
                .then(
                        Commands.argument("key", IntegerArgumentType.integer()/*StringArgumentType.string()*/)
                        .executes(c -> {
                            c.getSource().sendFeedback(new TranslationTextComponent("commands.reg_command.key", ""/*StringArgumentType.getString(c, "key")*/), true);
                                //System.out.println("Key is " + StringArgumentType.getString(c, "key"));
                            return 1;
                        })
                        .then(
                                Commands.argument("command", StringArgumentType.string())
                                .executes(c -> {
                                    // done
                                    int key = IntegerArgumentType.getInteger(c, "key");
                                    String command = StringArgumentType.getString(c, "command");
                                    if(key >= 39 && key <= 96 || key >= 320 && key <= 336) {
                                        c.getSource().sendFeedback(new TranslationTextComponent("commands.reg_command.key", GLFW.glfwGetKeyName(key, GLFW.glfwGetKeyScancode(key))/*StringArgumentType.getString(c, "key")*/), true);
                                        c.getSource().sendFeedback(new TranslationTextComponent("commands.reg_command.command", command), true);
                                        settedKey = key;
                                        settedCommand = command;

                                    } else {
                                        c.getSource().sendErrorMessage(new TranslationTextComponent("commands.reg_command.no_key_arg"));
                                    }
                                    return 1;
                                })
                        ).executes(c -> {
                            c.getSource().sendErrorMessage(new TranslationTextComponent("commands.reg_command.no_command_arg"));
                            return 1;
                        })
                )
                .executes(c -> {
                    c.getSource().sendErrorMessage(new TranslationTextComponent("commands.reg_command.no_key_arg"));
                    //System.out.println("Called command without key!");
                    return 1;
                })
//                .then(
//                        Commands.argument("command", StringArgumentType.string())
//                        .executes(c -> {
//                            c.getSource().sendFeedback(new TranslationTextComponent("commands.reg_command.command", StringArgumentType.getString(c, "command")), true);
//                            return 1;
//                        })
//                )
//                .executes(c -> {
//                    c.getSource().sendErrorMessage(new TranslationTextComponent("commands.reg_command.no_command_arg"));
//                    return 1;
//                })
        );
    }

    public int getSettedKey() {
        return settedKey;
    }

    public String getSettedCommand() {
        return settedCommand;
    }
}
