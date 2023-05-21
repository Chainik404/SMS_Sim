import java.awt.Color;

public class UIUtils {
    public static Color getGradientColor(int value, int minValue, int maxValue, Color baseColor) {
        float normalizedValue = (float) (value - minValue) / (maxValue - minValue);
        int red = (int) (baseColor.getRed() * normalizedValue);
        int green = (int) (baseColor.getGreen() * normalizedValue);
        int blue = (int) (baseColor.getBlue() * normalizedValue);
        return new Color(red, green, blue);
    }
    // Color btsButtonColor = getGradientColor(BTS.Items.Count, 1, 5, Color.Green);
    // Color vdsButtonColor = getGradientColor(vds.Frequency, 1, 100, Color.Yellow);
}
