package service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormValidate {
    private Pattern pattern;
    private Matcher matcher;
    private static final String FULLNAME_PATTERN = "^(?=.{1,40}$)[a-zA-Z]+(?:[-'\\s][a-zA-Z]+)*$";
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&*~$]).{8,20}$";
    private static final String EMAIL_PATTERN =   "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    private static final String PHONE_PATTERN = "^(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$";

    public boolean validateFullName(final String fullName){
        pattern = Pattern.compile(FULLNAME_PATTERN);
        matcher = pattern.matcher(fullName);
        System.out.println("validateFullName: " + matcher.matches());
        return matcher.matches();
    }
    public boolean validatePassword(final String password){
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        System.out.println("validatePassword: " + matcher.matches());
        return matcher.matches();
    }
    public boolean validateEmail(final  String email){
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher =pattern.matcher(email);
        System.out.println("validateEmail: " + matcher.matches());
        return matcher.matches();
    }
    public boolean validatePhone(final String phone){
        pattern = Pattern.compile(PHONE_PATTERN);
        matcher = pattern.matcher(phone);
        System.out.println("validatePhone: " + matcher.matches());
        return matcher.matches();
    }
}
