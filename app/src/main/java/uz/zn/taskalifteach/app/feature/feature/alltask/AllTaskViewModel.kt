package uz.zn.taskalifteach.app.feature.feature.alltask

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alifteachtask.data.model.TaskEntity
import kotlinx.coroutines.launch
import uz.mayasoft.marta.wallet.data.utils.flow.launchWithState
import uz.zn.taskalifteach.doimain.TaskInteractor
import javax.inject.Inject

class AllTaskViewModel @Inject constructor(
    private val taskInteractor: TaskInteractor
) :ViewModel() {

    private val _allTaskLiveData = MutableLiveData<AllTaskResource>()
    val taskAllLiveData: LiveData<AllTaskResource> = _allTaskLiveData

    private val _deleteTaskLiveData = MutableLiveData<TaskDeleteResource>()
    val deleteAllLiveData: LiveData<TaskDeleteResource> = _deleteTaskLiveData

    fun getAllTaskList() {
        viewModelScope.launch {
            taskInteractor.getAlltasks()
                .launchWithState(
                    onStart = { _allTaskLiveData.postValue(AllTaskResource.Loading)},
                    onSuccess = {_allTaskLiveData.postValue(AllTaskResource.Success(it))},
                    onFailure = {_allTaskLiveData.postValue(AllTaskResource.Failure(it))}
                )
        }
    }
    fun deleteTask(taskEntity: TaskEntity) {
        viewModelScope.launch {
            taskInteractor.deleteTasks(taskEntity)
                .launchWithState(
                    onStart = { _deleteTaskLiveData.postValue(TaskDeleteResource.Loading)},
                    onSuccess = {_deleteTaskLiveData.postValue(TaskDeleteResource.Success(it))},
                    onFailure = {_deleteTaskLiveData.postValue(TaskDeleteResource.Failure(it))}
                )
        }
    }



}