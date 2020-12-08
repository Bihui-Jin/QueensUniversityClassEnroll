package queens.enrollClass;

public class Main {
    /**
     * @Author: Bihui Jin
     * @Description: auto get enrolled into all classes in the shopping car -- Queen's University at Kingston
     * This project uses selenium.jar getting from https://www.selenium.dev/downloads/ or find it under jar file from the project
     * and its related browser driver chromedriver.exe getting from https://chromedriver.chromium.org/home or find it under the project file
     * for browser driver you may need to set your system variable first
     * (right click This PC->properties->advanced system setting->environment variables->system variable->select the variable name Path->Edit->Browse->select chromedriver.exe)
     * @TODO: the user might require to increase the time response allowance (variable timeOut) from 10 to a higher value
     */

    public static void main(String[] args)throws Exception{
        String solus = "https://my.queensu.ca/sidebar/20";
        String netId = "Your Queens's NetId";
        String pwd = "Your Password";
        String term ="2021 Winter";
        int timeOut = 10;
        Login login = new Login();
        login.LoginSolus(solus,netId,pwd,term,timeOut);
    }
}
