package com.ryde.assignment.nyinyi.contactsapp.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ryde.assignment.nyinyi.contactsapp.api.ContactsApi
import com.ryde.assignment.nyinyi.contactsapp.data.entity.Contact
import com.ryde.assignment.nyinyi.contactsapp.utils.STARTING_PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException

class ContactsDataSource(private val contactsApi: ContactsApi) :
    PagingSource<Int, Contact>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Contact> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = contactsApi.getContacts(params.loadSize, page)
            val contacts = response.data
            LoadResult.Page(
                data = contacts,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = page + 1
            )

        } catch (exception: IOException) {
            val error = IOException("Please Check Internet Connection")
            LoadResult.Error(error)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, Contact>): Int? {
        return state.anchorPosition
    }
}