package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.lire.Feature;
import br.com.antoniogabriel.lirelab.lire.QueryRunner;
import br.com.antoniogabriel.lirelab.lire.QueryRunnerFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static java.util.Collections.EMPTY_LIST;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CollectionServiceTest {

    public static final String ANY_NAME = "";
    public static final String ANY_PATH = "";

    @Mock private CollectionRepository collectionRepository;
    @Mock private CollectionsMonitor collectionsMonitor;
    @Mock private QueryRunnerFactory queryRunnerFactory;
    @Mock private PathResolver resolver;
    @Mock private QueryRunner queryRunner;

    private Runnable SOME_CALLBACK = () -> {};

    private CollectionService service;

    @Before
    public void  setUp() throws Exception {
        resolver = new PathResolver();
        service = new CollectionService(resolver,
                                        collectionRepository,
                                        collectionsMonitor,
                                        queryRunnerFactory);
    }

    @Test
    public void shouldStartMonitoringCollections() throws Exception {
        verify(collectionsMonitor).startMonitoringCollectionsDeleteAndUpdate();
    }

    @Test
    public void shouldBuildTaskToCreateCollectionAndMonitorItsCompletion() throws Exception {
        String name = ANY_NAME;
        String path = ANY_PATH;
        List<Feature> features = EMPTY_LIST;

        CreateCollectionTask task = service.getTaskToCreateCollection(name, path, features);

        assertNotNull(task);
        verify(collectionsMonitor).bindListenersTo(task);
    }

    @Test
    public void shouldGetCollections() throws Exception {
        service.getCollections();

        verify(collectionRepository).getCollections();
    }

    @Test
    public void shouldRegisterListenersForCollectionsChanges() throws Exception {
        service.addCollectionsChangeListener(SOME_CALLBACK);

        verify(collectionsMonitor).addListener(SOME_CALLBACK);
    }

    @Test
    public void shouldRunQuery() throws Exception {
        Collection collection = new Collection();
        Feature feature = Feature.CEDD;
        Image queryImage = new Image("", "");

        given(queryRunnerFactory.createQueryRunner(resolver))
                .willReturn(queryRunner);

        service.runQuery(collection, feature, queryImage);

        verify(queryRunner).runQuery(collection, feature, queryImage);
    }
}
