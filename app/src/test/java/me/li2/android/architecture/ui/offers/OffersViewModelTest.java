package me.li2.android.architecture.ui.offers;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;

import com.google.common.collect.Lists;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import arch.Resource;
import me.li2.android.architecture.data.model.BannerImage;
import me.li2.android.architecture.data.model.Offer;
import me.li2.android.architecture.data.repository.OffersRepository;
import me.li2.android.architecture.ui.offers.view.OffersNavigator;
import me.li2.android.architecture.ui.offers.viewmodel.OffersUiModel;
import me.li2.android.architecture.ui.offers.viewmodel.OffersViewModel;
import me.li2.android.architecture.utils.ResourceProvider;

import static org.mockito.Mockito.when;

/**
 *
 * LiveData unit test refer:
 *
 * [1] android-architecture-components -> GithubBrowserSample -> RepoFragmentTest.java
 *
 * [2] Android Architecture Components: Testing your ViewModel LiveData
 *     https://medium.com/exploring-android/android-architecture-components-testing-your-viewmodel-livedata-70177af89c6e
 *
 * [3] Failed: Method getMainLooper in android.os.Looper not mocked.
 *     https://medium.com/pxhouse/unit-testing-with-mutablelivedata-22b3283a7819
 *
 * [4] How To Unit Test LiveData and Lifecycle Components?
 *     https://proandroiddev.com/how-to-unit-test-livedata-and-lifecycle-components-8a0af41c90d9
 *     How To JUnit postValue?
 *     How To Observe LiveData In JUnit Test?
 *
 *
 * Created by weiyi on 2/8/18.
 * https://github.com/li2
 */
public class OffersViewModelTest {

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    private static List<Offer> OFFERS;

    @Mock
    private OffersRepository mOffersRepository;

    @Mock
    private ResourceProvider mResourceProvider;

    @Mock
    private OffersNavigator mNavigator;

    private OffersViewModel mViewModel;
    private MutableLiveData<Resource<List<Offer>>> mOffersLiveData;

    @Mock
    private Observer<OffersUiModel> mUiModelObserver;


    @Before
    public void setupOffersViewModel() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        mViewModel = new OffersViewModel(mOffersRepository, mResourceProvider, mNavigator);
        mOffersLiveData = new MutableLiveData<>();

        // Create mock offer data
        OFFERS = Lists.newArrayList(
                composeMockOffer("hotel", "offer1", 1, 3, "Shanghai", "Wenxi St", "highlights1", "facilities1", "fine print 1", "getting there 1", "photo id 1"),
                composeMockOffer("hotel", "offer2", 2, 4, "Sydney", "Baker St", "highlights2", "facilities2", "fine print 2", "getting there 2", "photo id 2"),
                composeMockOffer("tour", "offer3", 2, 4, "New Zealand", "Programmer St", "highlights3", "facilities3", "fine print 3", "getting there 3", "photo id 3")
        );
    }

    private Offer composeMockOffer(String type, String name, int minNights, int maxNights,
                                   String location, String hotelLocation, String highlights,
                                   String facilities, String finePrint, String gettingThere,
                                   String photoCloudinaryId) {
        Offer offer = new Offer();
        offer.type = type;
        offer.name = name;
        offer.minNumNights = minNights;
        offer.maxNumNights = maxNights;
        offer.locationHeading = location;
        offer.location = hotelLocation;
        offer.highlights = highlights;
        offer.facilities = facilities;
        offer.finePrint = finePrint;
        offer.gettingThere = gettingThere;
        BannerImage bannerImage = new BannerImage();
        bannerImage.cloudinaryId = photoCloudinaryId;
        offer.images = Lists.newArrayList(bannerImage);
        return offer;
    }

    @Test
    public void getOffersModel_emits_whenAllTasks() {
        Observer<OffersUiModel> uiModelObserver = offersUiModel -> {
            Assert.assertEquals(offersUiModel.isOffersListVisible(), true);
            Assert.assertEquals(offersUiModel.getItemList().get(0).name, "offer1");
            Assert.assertEquals(offersUiModel.getItemList().get(1).name, "offer2");
        };

        // return mock data
        when(mOffersRepository.loadOffers(true)).thenReturn(mOffersLiveData);
        mOffersLiveData.postValue(Resource.success(OFFERS));

        // observe on the MutableLiveData with an observer
        mViewModel.getUiModel(true).observeForever(uiModelObserver);

        // assert
//        OffersUiModel offersUiModel = null;
//        verify(mUiModelObserver).onChanged(offersUiModel);
    }
}
