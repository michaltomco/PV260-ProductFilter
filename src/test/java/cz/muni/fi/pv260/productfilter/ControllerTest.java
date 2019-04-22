package cz.muni.fi.pv260.productfilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class ControllerTest {

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorParamLoggerCantBeNull() {
        Controller controller = new Controller(mock(Input.class), mock(Output.class), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorParamInputCantBeNull() {
        Controller controller = new Controller(null, mock(Output.class), mock(Logger.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorParamOutputCantBeNull() {
        Controller controller = new Controller(mock(Input.class), null, mock(Logger.class));
    }

    @Test
    public void testCorrectlyFilteredProductsToOutput() throws ObtainFailedException {
        Input input = mock(Input.class);
        Product blueProduct1 = new Product(0, "First", Color.BLUE, java.math.BigDecimal.ONE);
        Product blueProduct2 = new Product(1, "Second", Color.BLUE, java.math.BigDecimal.TEN);
        Product redProduct = new Product(2, "Third", Color.RED, java.math.BigDecimal.ZERO);
        Mockito.doReturn(Arrays.asList(blueProduct1, blueProduct2, redProduct)).when(input).obtainProducts();

        Output output = mock(Output.class);
        Logger logger = mock(Logger.class);

        Controller controller = new Controller(input, output, logger);        
        controller.select(new ColorFilter(Color.BLUE));

        verify(output).postSelectedProducts(Arrays.asList(blueProduct1, blueProduct2));
    }
    
    @Test
    public void testLoggedSuccessfullyFilteredProducts() throws ObtainFailedException {
        Input input = mock(Input.class);
        Product blueProduct1 = new Product(0, "First", Color.BLUE, java.math.BigDecimal.ONE);
        Product blueProduct2 = new Product(1, "Second", Color.BLUE, java.math.BigDecimal.TEN);
        Product redProduct = new Product(2, "Third", Color.RED, java.math.BigDecimal.ZERO);
        Mockito.doReturn(Arrays.asList(blueProduct1, blueProduct2, redProduct)).when(input).obtainProducts();

        Output output = mock(Output.class);
        Logger logger = mock(Logger.class);

        Controller controller = new Controller(input, output, logger);        
        controller.select(new ColorFilter(Color.BLUE));

        verify(logger).log(Controller.class.getSimpleName(),"Successfully selected 2 out of 3 available products.");
    }
    
    @Test
    public void testLoggedException() throws ObtainFailedException {
        Output output = mock(Output.class);
        Logger logger = mock(Logger.class);        
        Input input = mock(Input.class);
        
        Exception mockException = new ObtainFailedException();
        when(input.obtainProducts()).thenThrow(mockException);


        Controller controller = new Controller(input, output, logger);        
        controller.select(new ColorFilter(Color.BLUE));

        verify(logger).log(Controller.class.getSimpleName(), "Filter procedure failed with exception: " + mockException);
    }
    
    @Test
    public void testNothingToOutputWhenException() throws ObtainFailedException {
        Output output = mock(Output.class);
        Logger logger = mock(Logger.class);        
        Input input = mock(Input.class);
        
        Exception mockException = new ObtainFailedException();
        when(input.obtainProducts()).thenThrow(mockException);


        Controller controller = new Controller(input, output, logger);        
        controller.select(new ColorFilter(Color.BLUE));

        verifyZeroInteractions(output);
    }

}
