package org.neu.mvc;

/**
 * Interface to support a UI for the application.
 */
public interface View {

  /**
   * Method to bind the buttons in the view to methods in the controller (which ultimately will
   * call methods in the model).
   *
   * @param controller
   */
  void setFeatures(Controller controller);

  /**
   * Method to update the project preview panels.
   *
   * @param controller
   */
  void updateProjectPreview(Controller controller);

  /**
   * Method to highlight the similarities between the projects.
   */
  void highlightProjects();

  /**
   * Method to pass along an error message or notification to the user.
   *
   * @param msg String, notification or error message
   */
  void notification(String msg);
}
