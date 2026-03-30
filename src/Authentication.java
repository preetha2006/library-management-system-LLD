//ABSTARCT BASE CLASS
import java.io.*;
import java.util.*;

public abstract class Authentication {

    protected String username;
    protected String password;

    public Authentication(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }

    public static boolean validatePassword(String password) {
        if (password.length() < 8) {
            System.out.println("Password must be at least 8 characters.");
            return false;
        }
        boolean hasUpper = false, hasLower = false, hasDigit = false, hasSpecial = false;
        String special = "@#$%!&*";
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isLowerCase(c)) hasLower = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else if (special.indexOf(c) >= 0) hasSpecial = true;
        }
        if (!hasUpper)   { System.out.println("Need at least 1 uppercase letter."); return false; }
        if (!hasLower)   { System.out.println("Need at least 1 lowercase letter."); return false; }
        if (!hasDigit)   { System.out.println("Need at least 1 number."); return false; }
        if (!hasSpecial) { System.out.println("Need at least 1 special character (@#$%!&*)."); return false; }
        return true;
    }

    public static void saveToFile(String filename, String data) {
        try {
            File dir = new File("data");
            if (!dir.exists()) dir.mkdir();
            FileWriter fw = new FileWriter("data/" + filename, true);
            fw.write(data + "\n");
            fw.close();
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    public static List<String[]> loadFromFile(String filename) {
        List<String[]> records = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("data/" + filename));
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty())
                    records.add(line.split(","));
            }
            br.close();
        } catch (IOException e) {
        }
        return records;
    }

    public static boolean usernameExists(String filename, String username) {
        List<String[]> records = loadFromFile(filename);
        for (String[] r : records) {
            if (r[0].equalsIgnoreCase(username)) return true;
        }
        return false;
    }

    public abstract void showMenu();
}