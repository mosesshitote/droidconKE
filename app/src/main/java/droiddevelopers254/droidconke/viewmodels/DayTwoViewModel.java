package droiddevelopers254.droidconke.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;

import droiddevelopers254.droidconke.database.entities.StarredSessionEntity;
import droiddevelopers254.droidconke.datastates.SessionsState;
import droiddevelopers254.droidconke.repository.DayTwoRepo;
import droiddevelopers254.droidconke.repository.StarrSessionRepo;

public class DayTwoViewModel extends ViewModel{
    private MediatorLiveData<SessionsState> sessionsStateMediatorLiveData;
    private DayTwoRepo dayTwoRepo;
    private StarrSessionRepo starrSessionRepo;

    public DayTwoViewModel(){
        dayTwoRepo = new DayTwoRepo();
        sessionsStateMediatorLiveData = new MediatorLiveData<>();
        starrSessionRepo = new StarrSessionRepo();
    }

    public LiveData<SessionsState> getSessions(){
        return sessionsStateMediatorLiveData;
    }

    public void getDayTwoSessions(){
        final LiveData<SessionsState> sessionsStateLiveData = dayTwoRepo.getDayTwoSessions();
        sessionsStateMediatorLiveData.addSource(sessionsStateLiveData,
                sessionsStateMediatorLiveData ->{
                    if (this.sessionsStateMediatorLiveData.hasActiveObservers()){
                        this.sessionsStateMediatorLiveData.removeSource(sessionsStateLiveData);
                    }
                    this.sessionsStateMediatorLiveData.setValue(sessionsStateMediatorLiveData);
                });
    }
    public void starSession(StarredSessionEntity starredSessionEntity){
        starrSessionRepo.starrSession(starredSessionEntity);
    }

    public void unStarredSession(int sessionId, String dayNumber){
        starrSessionRepo.unStarrSession(sessionId, dayNumber);
    }

    public void updateSession(int sessionId, boolean isStarred){
        dayTwoRepo.updateSession(sessionId, isStarred);
    }

    public void isSessionStarred(int sessionId){
        dayTwoRepo.isSessionStarred(sessionId);
    }
}
