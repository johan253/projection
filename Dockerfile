FROM openjdk:19-jdk

COPY out/artifacts/projector_jar/projector.jar projector.jar

COPY javafx-sdk-21.0.2 javafx-sdk-21.0.2

ENTRYPOINT ["java", "--module-path", "/javafx-sdk-21.0.2/lib", "--add-modules", "javafx.controls,javafx.fxml", "-jar", "projector.jar", "-Dprism.verbose=true"]