package net.staro.lego.setting.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import java.lang.reflect.Field;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class Bind {
    private int key;

    public static Bind none() {
        return new Bind(-1);
    }

    public boolean isEmpty() {
        return key < 0;
    }

    public String toString() {
        return isEmpty() ? "None" : (key < 0 ? "None" : capitalise(InputUtil.fromKeyCode(key, 0).getTranslationKey()));
    }

    private String capitalise(String str) {
        if (str.isEmpty()) return "";
        return Character.toUpperCase(str.charAt(0)) + (str.length() != 1 ? str.substring(1).toLowerCase() : "");
    }

    public String getBindName() {
        String kn = this.key > 0 ? GLFW.glfwGetKeyName(this.key, GLFW.glfwGetKeyScancode(this.key)) : "None";
        if (kn == null) {
            try {
                for (Field declaredField : GLFW.class.getDeclaredFields()) {
                    if (declaredField.getName().startsWith("GLFW_KEY_")) {
                        int a = (int) declaredField.get(null);
                        if (a == this.key) {
                            String nb = declaredField.getName().substring("GLFW_KEY_".length());
                            kn = nb.substring(0, 1).toUpperCase() + nb.substring(1).toLowerCase();
                        }
                    }
                }
            } catch (Exception ignore) {
                kn = "unknown." + this.key;
            }
        }

        return this.key == -1 ? "None" : (Objects.requireNonNull(kn)).toUpperCase();
    }

}
