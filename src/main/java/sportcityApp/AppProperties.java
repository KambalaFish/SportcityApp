package sportcityApp;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AppProperties {
    public String getServerHostname(){
        return "http://localhost:8080/";
    }
}
