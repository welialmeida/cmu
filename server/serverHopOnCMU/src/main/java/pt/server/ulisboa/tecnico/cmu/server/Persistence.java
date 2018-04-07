package pt.server.ulisboa.tecnico.cmu.server;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.PublicKey;
import java.util.TreeMap;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;

//TODO change public Key key in map with bus ticket code - persistence
public class Persistence {

    public static void store(PublicKey pubK, Account account) throws IOException {

        FileOutputStream fos = new FileOutputStream("./Accounts/" + calculateMD5(pubK) + ".cli");
        ObjectOutputStream os = new ObjectOutputStream(fos);
        os.writeObject(account);
        os.close();
        fos.close();
    }

    public static Account read(PublicKey pubK) throws IOException, ClassNotFoundException {

        FileInputStream fis = new FileInputStream("./Accounts/" + calculateMD5(pubK) + ".cli");
        ObjectInputStream ois = new ObjectInputStream(fis);
        Account account = (Account) ois.readObject();
        ois.close();
        fis.close();
        return account;
    }

    public static String calculateMD5(PublicKey pubK) throws IOException {

        String filename = "./Accounts/temp.cli";
        FileOutputStream temp = new FileOutputStream(filename);
        ObjectOutputStream oos = new ObjectOutputStream(temp);
        oos.writeObject(pubK);

        FileInputStream i = new FileInputStream(new File(filename));
        String md5 = DigestUtils.md5Hex(IOUtils.toByteArray(i));
        oos.close();
        temp.close();
        return md5;
    }

    public static boolean exists(PublicKey pubK) {

        File files[];
        File dir = new File("./Accounts/");
        files = dir.listFiles();

        for (File file : files) {
            try {
                if (file.getName().equals(calculateMD5(pubK) + ".cli")) {
                    return true;

                }
            } catch (IOException e) {
                return false;
            }
        }
        return false;
    }

    public static TreeMap<String, Account> getAccounts() {
        File files[];
        File dir = new File("./Accounts/");
        files = dir.listFiles();
        TreeMap<String, Account> accounts = new TreeMap<String, Account>();

        for (File file : files) {
            try {
                if (file.getName().equals("temp.cli")) {
                    continue;
                }
                FileInputStream fis = new FileInputStream("./Accounts/" + file.getName());
                ObjectInputStream ois = new ObjectInputStream(fis);
                String key = file.getName().replaceAll(".cli", "");
                accounts.put(key, (Account) ois.readObject());
                ois.close();
                fis.close();

            } catch (IOException | ClassNotFoundException e) {
                System.out.println("PROBLEMS WITH: \'" + file.getName() + "\'\n" + e.getMessage());
                return null;
            }
        }

        return accounts;

    }

    public static void deleteAllAccounts() {
        File files[];
        File dir = new File("./Accounts/");
        files = dir.listFiles();
        for (File file : files) {
            file.delete();
        }
    }

    public static void deleteAccount(PublicKey pubK) throws IOException {
        File files[];
        File dir = new File("./Accounts/");
        files = dir.listFiles();
        for (File file : files) {
            if (file.getName().equals(calculateMD5(pubK) + ".cli")) {
                file.delete();
                break;
            }

        }
    }


}
