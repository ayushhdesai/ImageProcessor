import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;

import controller.FeaturesImpl;
import view.View;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

/**
 * Junit test file for FeaturesImpl class.
 */
public class FeaturesImplTest {

  @Mock
  private View mockView;
  private FeaturesImpl features;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    features = new FeaturesImpl(new HashMap<>(), mockView);
  }

  @Test
  public void testSaveImage() {
    features.saveImage();
    verify(mockView, times(1)).saveImage();
  }

}
