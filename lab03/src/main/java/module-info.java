module lab03.main {
    requires com.google.gson;
    requires jdk.unsupported;

    exports pl.edu.pwr.lgawron.customer;
    exports pl.edu.pwr.lgawron.employee;
    exports pl.edu.pwr.lgawron.manufacturer;

    opens pl.edu.pwr.lgawron.businesslogic.models to com.google.gson;
}