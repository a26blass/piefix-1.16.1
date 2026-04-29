package vldy.piechartfix;

import net.fabricmc.loader.api.FabricLoader;
import java.io.*;
import java.nio.file.*;

public class PieFixConfig {
    private static float scale = 1.0f;
    private static int offsetX = 0;
    private static int offsetY = 0;

    public static float getScale() { return scale; }
    public static int getOffsetX() { return offsetX; }
    public static int getOffsetY() { return offsetY; }

    public static void load() {
        Path configFile = FabricLoader.getInstance().getConfigDir().resolve("piefix.txt");
        if (Files.exists(configFile)) {
            try (BufferedReader reader = new BufferedReader(new FileReader(configFile.toFile()))) {
                String line1 = reader.readLine();
                String line2 = reader.readLine();
                String line3 = reader.readLine();
                if (line1 != null) scale = Float.parseFloat(line1.trim());
                if (line2 != null) offsetX = Integer.parseInt(line2.trim());
                if (line3 != null) offsetY = Integer.parseInt(line3.trim());
            } catch (Exception e) {
                scale = 1.0f;
                offsetX = 0;
                offsetY = 0;
            }
        } else {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(configFile.toFile()))) {
                writer.write("1.0\n0\n0");
            } catch (Exception ignored) {}
        }
    }
}