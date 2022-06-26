import java.io.*;
import java.util.Properties;
import java.util.Scanner;

public class EasyGamma {
    private final String _path;

    public EasyGamma(String minecraftPath) {
        String appdata = System.getenv("APPDATA");
        _path = appdata + minecraftPath;
    }

    public static void main(String[] args) {
        EasyGamma easyGamma = new EasyGamma("\\.minecraft\\options.txt");
        System.out.println("Please enter the desired gamma value: ");

        Scanner scanner = new Scanner(System.in);
        String gammaValueString = scanner.next();

        try {
            int gammaValueInt = Integer.parseInt(gammaValueString);

            if(gammaValueInt >= 0 && gammaValueInt <= 20) {

                easyGamma.readAndWriteProperty("gamma", gammaValueInt + ".0");
                easyGamma.replaceEqualSignByColon();
                System.out.println("The gamma value was now set to " + gammaValueInt + ".0");

            } else
                System.out.println("Please enter a number between 0 and 10");

        } catch (NumberFormatException e) {
            System.out.println("Please enter a number");
        }
        scanner.close();
    }

    private void readAndWriteProperty(String name, String value) {
        Properties prop = new Properties();
        try (InputStream input = new FileInputStream(_path)) {
            prop.load(input);
            input.close();

            prop.setProperty(name, value);

            OutputStream output = new FileOutputStream(_path);
            prop.store(output, null);
            output.close();
        } catch (IOException ex) {
            System.out.println("Problem reading/writing with the file: " + _path);
            ex.printStackTrace();
        }
    }

    private void replaceEqualSignByColon() {
        try {
            BufferedReader file = new BufferedReader(new FileReader(_path));
            StringBuffer inputBuffer = new StringBuffer();
            String line;

            while ((line = file.readLine()) != null) {
                inputBuffer.append(line);
                inputBuffer.append('\n');
            }
            file.close();

            String inputStr = inputBuffer.toString();
            inputStr = inputStr.replace("=", ":");

            FileOutputStream out = new FileOutputStream(_path);
            out.write(inputStr.getBytes());
            out.close();
        }
        catch (IOException ex) {
            System.out.println("Problem reading/writing with the file: " + _path);
            ex.printStackTrace();
        }
    }
}
