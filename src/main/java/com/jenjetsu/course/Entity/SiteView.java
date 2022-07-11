package com.jenjetsu.course.Entity;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class SiteView {
        public SimpleIntegerProperty index;
        public SimpleStringProperty key;
        public SimpleStringProperty value;
        public SimpleBooleanProperty status;
        public SimpleIntegerProperty indexProperty() {
            return index;
        }
        public SimpleStringProperty keyProperty() {
            return key;
        }
        public SimpleStringProperty valueProperty() {
            return value;
        }
        public SimpleBooleanProperty statusProperty() {
            return status;
        }
}
