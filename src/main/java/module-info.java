module com.jenjetsu.course {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.base;
    requires javafx.fxml;


    opens com.jenjetsu.course to javafx.fxml;
    opens com.jenjetsu.course.Controllers to javafx.fxml;
    exports com.jenjetsu.course;
    exports com.jenjetsu.course.Controllers;
    exports com.jenjetsu.course.utils;
}