package com.ryde.assignment.nyinyi.contactsapp.data.remotediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.ryde.assignment.nyinyi.contactsapp.api.ContactsApi
import com.ryde.assignment.nyinyi.contactsapp.data.db.AppDataBase
import com.ryde.assignment.nyinyi.contactsapp.data.entity.Contact
import com.ryde.assignment.nyinyi.contactsapp.data.entity.RemoteKeys
import com.ryde.assignment.nyinyi.contactsapp.utils.STARTING_PAGE_INDEX
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException


@ExperimentalPagingApi
class ContactsRemoteMediator(
    private val service: ContactsApi,
    private val db: AppDataBase
) : RemoteMediator<Int, Contact>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Contact>
    ): MediatorResult {
        val key = when (loadType) {
            LoadType.REFRESH -> {

                if (db.contactsDao.count() > 0) return MediatorResult.Success(false)
                null
            }
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)

            }
            LoadType.APPEND -> {

                getKey()
            }
        }

        try {

            if (key != null) {
                if (key.isEndReached) return MediatorResult.Success(endOfPaginationReached = true)
            }

            val page: Int = key?.nextKey ?: STARTING_PAGE_INDEX

            delay(2000)

            val apiResponse = service.getContacts(state.config.pageSize, page)

            val contactsList = apiResponse.data

            val endOfPaginationReached = apiResponse.page > apiResponse.total_pages

            db.withTransaction {

                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1

                db.remoteKeysDao.insertKey(
                    RemoteKeys(
                        0,
                        prevKey = prevKey,
                        nextKey = nextKey,
                        isEndReached = endOfPaginationReached
                    )
                )
                db.contactsDao.insertMultipleContacts(contactsList)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(Throwable("Network connection error!"))
        }
    }

    private suspend fun getKey(): RemoteKeys? {
        return db.remoteKeysDao.getKeys().firstOrNull()
    }


}