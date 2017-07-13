package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.util.LireLabUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class CollectionRepositoryUnitTest {

    @Mock private Subfolders subfolders;
    @Mock private Iterator<Path> subfoldersIterator;
    @Mock private Path collectionPath1;
    @Mock private Path collectionPath2;
    @Mock private CollectionAssembler collectionAssembler;
    @Mock private LireLabUtils lireLabUtils;

    private Collection collection1 = new Collection("Collection1");
    private Collection collection2 = new Collection("Collection2");

    private CollectionRepository repository;

    @Before
    public void setUp() throws Exception {
        repository = new TestableCollectionRepository();
    }

    @Test
    public void shouldReturnEmptyCollectionListWhenCollectionDirectoryDontExist() throws Exception {
        given(lireLabUtils.collectionsDirectoryExist()).willReturn(false);

        List<Collection> collections = repository.getCollections();

        assertTrue(collections.isEmpty());
    }

    @Test
    public void shouldReturnCollectionsInCollectionsDirectory() throws Exception {
        given(lireLabUtils.collectionsDirectoryExist()).willReturn(true);

        given(subfolders.iterator()).willReturn(subfoldersIterator);

        given(subfoldersIterator.hasNext()).willReturn(true, true, false);
        given(subfoldersIterator.next()).willReturn(collectionPath1, collectionPath2);

        given(lireLabUtils.isCollection(collectionPath1)).willReturn(true);
        given(lireLabUtils.isCollection(collectionPath2)).willReturn(true);

        given(collectionAssembler.assembleCollectionFrom(collectionPath1)).willReturn(collection1);
        given(collectionAssembler.assembleCollectionFrom(collectionPath2)).willReturn(collection2);

        List<Collection> collections = repository.getCollections();

        assertTrue(collections.contains(collection1));
        assertTrue(collections.contains(collection2));
    }

    private class TestableCollectionRepository extends CollectionRepository {

        public TestableCollectionRepository() {
            super(lireLabUtils, collectionAssembler);
        }

        @Override
        protected Subfolders getSubfoldersOf(Path dir) throws IOException {
            return subfolders;
        }
    }
}
