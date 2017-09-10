package com.JetBrains;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Program: UserLogin
 * Author: Jeffrey Cutcliffe
 * Date: 09.2017
 * This program checks the validity of user logins to a specified specification list and writes
 * to a text file, the specifications are as follows
 *      -must contain one uppercase
 *      -must contain one lowercase
 *      -must be at least 5 characters long
 *      -must contain one digit
 *      -must contain one of the following special characters, !@#$
 */

public class UserLogin {

    public static void main(String[] args) {
        UserLogin login = new UserLogin();
        login.greetUser();
        boolean finished = false;
        StringBuffer report = new StringBuffer();
        do {
            String[] UserName = login.readUser();
            boolean validCase = login.checkCase(UserName[0]);
            boolean validLength = login.checkLength(UserName[0]);
            boolean validDigit = login.checkDigit(UserName[0]);
            boolean validSpecialChar = login.checkSpecialCharacters(UserName[0]);
            boolean validWhiteSpace = login.whiteSpaceCheck(UserName[0]);
            boolean validity = login.checkValidity(validCase, validLength, validDigit, validSpecialChar, validWhiteSpace);
            login.printUser(validity, UserName[0], validCase, validLength, validDigit, validSpecialChar);
            StringBuffer finalReport = login.addToReport(UserName[0], validity, validCase, validLength, validDigit, validSpecialChar, report);
            String end = "no";
            if(UserName[1].equalsIgnoreCase(end)){
                login.printReport(finalReport);
                finished = true;
            }
        }while(!finished);

    }

    public UserLogin(){
    }

    public void greetUser(){
        System.out.println("Hello, this program evaluates a user login for validity.");
    }

    public String[] readUser(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("User Login: ");
        String[] LoginFromUser = new String[2];
        LoginFromUser[0] = scanner.next();
        System.out.println("Do you have another user login to validate?");
        LoginFromUser[1] = scanner.next();
        return LoginFromUser;
    }

    public boolean checkCase(String LoginFromUser){
        //System.out.println("checkCase");
        boolean caseOk = false;
        boolean hasUpperCase = !LoginFromUser.equals(LoginFromUser.toLowerCase());
        boolean hasLowerCase = !LoginFromUser.equals(LoginFromUser.toUpperCase());
        if(hasUpperCase && hasLowerCase){
            caseOk = true;
        }
        //System.out.println(caseOk);
        return caseOk;
    }

    public boolean checkLength(String LoginFromUser){
        //System.out.println("checkLength");
        boolean lengthOk = false;
        lengthOk = LoginFromUser.length() >= 5;
        //System.out.println(lengthOk);
        return lengthOk;
    }

    public boolean checkDigit(String LoginFromUser){
        //System.out.println("checkDigits");
        boolean digitOk = false;
        for(char c : LoginFromUser.toCharArray()){
            if(Character.isDigit(c)){
                digitOk = true;
            }
        }
        //System.out.println(digitOk);
        return digitOk;
    }

    public boolean checkSpecialCharacters(String LoginFromUser){
        //System.out.println("checkSpecialCharacters");
        boolean specCharOk = false;
        String specialChar1 = "!";
        String specialChar2 = "@";
        String specialChar3 = "#";
        String specialChar4 = "$";
        if(LoginFromUser.contains(specialChar1) || LoginFromUser.contains(specialChar2)){
            specCharOk = true;
        }
        if(LoginFromUser.contains(specialChar3) || LoginFromUser.contains(specialChar4)){
            specCharOk = true;
        }
        //System.out.println(specCharOk);
        return specCharOk;
    }

    public boolean whiteSpaceCheck(String LoginFromUser){
        boolean whiteSpaceOk = true;
        if(LoginFromUser.indexOf(' ') >= 0){
            whiteSpaceOk = false;
        }
        if(LoginFromUser.indexOf('\t') >= 0){
            whiteSpaceOk = false;
        }
        if(LoginFromUser.contains("\n")){
            whiteSpaceOk = false;
        }
        return whiteSpaceOk;
    }

    public boolean checkValidity(boolean caseOk, boolean lengthOk, boolean digitOk, boolean specCharOk, boolean whiteSpaceOk){
        //System.out.println("checkValidity");
        boolean valid = false;
        if(caseOk && lengthOk && digitOk && specCharOk && whiteSpaceOk) {
            valid = true;
        }
        //System.out.println(valid);
        return valid;
    }

    public void printUser(boolean valid, String LoginFromUser, boolean caseOk, boolean lengthOk, boolean digitOk, boolean specCharOk){
        //System.out.println("printUser");
        if(valid){
            System.out.println("Login: " + LoginFromUser + " (valid)");
        }else {
            System.out.println("Login: " + LoginFromUser + " (invalid)");
            if(!specCharOk){
                System.out.println("    -- invalid special character");
            }
            if(!digitOk){
                System.out.println("    -- no digit");
            }
            if(!caseOk){
                System.out.println("    -- invalid case");
            }
            if(!lengthOk){
                System.out.println("    -- too short");
            }
        }
    }

    public StringBuffer addToReport(String LoginFromUser, boolean valid, boolean caseOk, boolean lengthOk, boolean digitOk, boolean specCharOk, StringBuffer report){
        //System.out.println("addToReport");
        report.append("\r\n");
        if(valid) {
            report.append("Login: " + LoginFromUser + " (valid)");
            report.append("\r\n");
        }else{
            report.append("Login: " + LoginFromUser + " (invalid)");
            report.append("\r\n");
            if(!specCharOk){
                report.append("    -- invalid special character");
                report.append("\r\n");
            }
            if(!digitOk){
                report.append("    -- no digit");
                report.append("\r\n");
            }
            if(!caseOk){
                report.append("    -- invalid case");
                report.append("\r\n");
            }
            if(!lengthOk){
                report.append("    -- too short");
                report.append("\r\n");
            }
        }
        return report;
    }

    public void printReport(StringBuffer report){
        //System.out.println("printReport");
        try{
            Files.write(Paths.get("./testfile.txt"), report.toString().getBytes());
        }catch(IOException e){
        }
    }
}
