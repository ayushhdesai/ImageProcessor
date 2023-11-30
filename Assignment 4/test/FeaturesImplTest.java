import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import controller.FeaturesImpl;
import view.MockView;
import view.View;


/**
 * Junit test file for FeaturesImpl class.
 */
public class FeaturesImplTest {
  private MockView mockView;
  private FeaturesImpl features;

  @Before
  public void setUp() {
    mockView = new MockView();
    features = new FeaturesImpl(new HashMap<>(), mockView);
  }

  @Test
  public void testVerticalFlip() {
    features.verticalFlip();
    assertTrue("Get Image method in view not called", mockView.getImageFlag());
    assertTrue("Display Image method in view not called", mockView.getDisplayImageFlag());
  }

  @Test
  public void testHorizontalFlip() {
    features.horizontalFlip();
    assertTrue("Get Image method in view not called", mockView.getImageFlag());
    assertTrue("Display Image method in view not called", mockView.getDisplayImageFlag());
  }

  @Test
  public void testVisualizeRedComponent() {
    features.visualizeRedComponent();
    assertTrue("Get Image method in view not called", mockView.getImageFlag());
    assertTrue("Display Image method in view not called", mockView.getDisplayImageFlag());
  }

  @Test
  public void testVisualizeGreenComponent() {
    features.visualizeGreenComponent();
    assertTrue("Get Image method in view not called", mockView.getImageFlag());
    assertTrue("Display Image method in view not called", mockView.getDisplayImageFlag());
  }

  @Test
  public void testVisualizeBlueComponent() {
    features.visualizeBlueComponent();
    assertTrue("Get Image method in view not called", mockView.getImageFlag());
    assertTrue("Display Image method in view not called", mockView.getDisplayImageFlag());
  }

  @Test
  public void testBlur() {
    features.blurImage();
    assertTrue("Get Image method in view not called", mockView.getImageFlag());
    assertTrue("Display Image method in view not called", mockView.getDisplayImageFlag());
  }

  @Test
  public void testSharpen() {
    features.applySharpen();
    assertTrue("Get Image method in view not called", mockView.getImageFlag());
    assertTrue("Display Image method in view not called", mockView.getDisplayImageFlag());
  }

}
