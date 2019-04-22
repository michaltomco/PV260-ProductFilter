package cz.muni.fi.pv260.productfilter;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AtLeastNOfFilterTest {

    @Test
    public void testMoreThanOneFilterDefined() {
        AtLeastNOfFilter filter = new AtLeastNOfFilter(2, new AlwaysTrueFilterMock(), new AlwaysTrueFilterMock());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNIsZero() {
        AtLeastNOfFilter filter = new AtLeastNOfFilter(0, new AlwaysTrueFilterMock());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNIsLessZero() {
        AtLeastNOfFilter filter = new AtLeastNOfFilter(-1, new AlwaysTrueFilterMock());
    }

    @Test(expected = FilterNeverSucceeds.class)
    public void testNHigherThanNumberOfFilters() {
        AtLeastNOfFilter filter = new AtLeastNOfFilter(2, new AlwaysTrueFilterMock());
    }

    @Test
    public void testExactly1FilterPassesFromTwo() {
        AtLeastNOfFilter filter = new AtLeastNOfFilter(1, new AlwaysFalseFilterMock(), new AlwaysTrueFilterMock());
        assertTrue(filter.passes(new Object()));
    }

    @Test
    public void testNoFilterPasses() {
        AtLeastNOfFilter filter = new AtLeastNOfFilter(1, new AlwaysFalseFilterMock(), new AlwaysFalseFilterMock());
        assertFalse(filter.passes(new Object()));
    }

    @Test
    public void testExactly2FiltersPass() {
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
