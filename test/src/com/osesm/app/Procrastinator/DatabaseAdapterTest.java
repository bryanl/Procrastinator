package com.osesm.app.Procrastinator;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

public class DatabaseAdapterTest extends AndroidTestCase {

    private static final String TEST_FILE_PREFIX = "test_";

    private DatabaseAdapter databaseAdapter;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), TEST_FILE_PREFIX);

        databaseAdapter = new DatabaseAdapter(context);
        databaseAdapter.open();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();

        databaseAdapter.close();
        databaseAdapter = null;
    }

    public void testPreconditions() {
        assertNotNull(databaseAdapter);
    }
}
