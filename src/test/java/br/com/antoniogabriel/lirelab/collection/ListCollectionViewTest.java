package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.FXMLTest;
import com.google.inject.AbstractModule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

@RunWith(MockitoJUnitRunner.class)
public class ListCollectionViewTest extends FXMLTest<ListCollectionFXML> {

    private static final List<Collection> COLLECTIONS = getCollections();

    @Mock private CollectionService collectionService;

    @Override
    public void init() throws Exception {
        given(collectionService.getCollections()).willReturn(COLLECTIONS);
    }

    @Override
    protected AbstractModule getBindings() {
        return new AbstractModule() {
            @Override
            protected void configure() {
                bind(CollectionService.class).toInstance(collectionService);
            }
        };
    }

    private static List<Collection> getCollections() {
        Collection collection1 = new Collection();
        collection1.setName("Collection1");

        Collection collection2 = new Collection();
        collection2.setName("Collection2");

        Collection collection3 = new Collection();
        collection3.setName("Collection3");

        Collection collection4 = new Collection();
        collection4.setName("Collection4");

        final ArrayList<Collection> cols = new ArrayList<>();
        cols.add(collection1);
        cols.add(collection2);
        cols.add(collection3);
        cols.add(collection4);

        return cols;
    }

    @Test
    public void shouldListCollections() throws Exception {
        verifyThat("Collection1", isVisible());
        verifyThat("Collection2", isVisible());
        verifyThat("Collection3", isVisible());
        verifyThat("Collection4", isVisible());
    }
}
