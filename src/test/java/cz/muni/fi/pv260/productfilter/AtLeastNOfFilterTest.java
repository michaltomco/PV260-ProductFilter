package cz.muni.fi.pv260.productfilter;

import org.junit.Test;
import static org.junit.Assert.*;

public class AtLeastNOfFilterTest {

    @Test
    public void testMoreThanOneFilterDefined() {
        AtLeastNOfFilter filter = new AtLeastNOfFilter(1, new AlwaysTrueFilterMock(), new AlwaysFalseFilterMock());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNCantBeZero() {
        AtLeastNOfFilter filter = new AtLeastNOfFilter(0, new AlwaysTrueFilterMock());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNCantBeLessThanZero() {
        AtLeastNOfFilter filter = new AtLeastNOfFilter(-1, new AlwaysTrueFilterMock());
    }

    @Test(expected = FilterNeverSucceeds.class)
    public void testNCantBeHigherThanNumberOfFilters() {
        AtLeastNOfFilter filter = new AtLeastNOfFilter(2, new AlwaysTrueFilterMock());
    }

    @Test
    public void testExactlyOneFilterPassesFromTwo() {
        AtLeastNOfFilter filter = new AtLeastNOfFilter(1, new AlwaysFalseFilterMock(), new AlwaysTrueFilterMock());
        assertTrue(filter.passes(new Object()));
    }

    @Test
    public void testNoFilterPassesFromTwo() {
        AtLeastNOfFilter filter = new AtLeastNOfFilter(1, new AlwaysFalseFilterMock(), new AlwaysFalseFilterMock());
        assertFalse(filter.passes(new Object()));
    }

    @Test
    public void testSameAmountOfFiltersPassAsN() {
        AtLeastNOfFilter filter = new AtLeastNOfFilter(2, new AlwaysTrueFilterMock(), new AlwaysTrueFilterMock());
        assertTrue(filter.passes(new Object()));
    }

    @Test
    public void testMoreFiltersPassThanNeeded() {
        AtLeastNOfFilter filter = new AtLeastNOfFilter(2, new AlwaysTrueFilterMock(), new AlwaysTrueFilterMock(), new AlwaysTrueFilterMock(), new AlwaysFalseFilterMock());
        assertTrue(filter.passes(new Object()));
    }

    private class AlwaysTrueFilterMock implements Filter<Object> {

        @Override
        public boolean passes(Object argument) {
            return true;
        }
    }

    private class AlwaysFalseFilterMock implements Filter<Object> {

        @Override
        public boolean passes(Object argument) {
            return false;
        }
    }

}
