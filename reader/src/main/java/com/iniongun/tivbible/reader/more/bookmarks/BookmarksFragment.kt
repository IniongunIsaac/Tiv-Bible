package com.iniongun.tivbible.reader.more.bookmarks

import com.iniongun.tivbible.common.base.BaseFragment
import com.iniongun.tivbible.reader.BR
import com.iniongun.tivbible.reader.R
import com.iniongun.tivbible.reader.databinding.BookmarksFragmentBinding
import javax.inject.Inject

/**
 * Created by Isaac Iniongun on 09/05/2020.
 * For Tiv Bible project.
 */

class BookmarksFragment : BaseFragment<BookmarksFragmentBinding, BookmarksViewModel>() {

    @Inject
    lateinit var bookmarksViewModel: BookmarksViewModel

    private lateinit var bookmarksFragmentBinding: BookmarksFragmentBinding

    override fun getViewModel() = bookmarksViewModel

    override fun getLayoutId() = R.layout.bookmarks_fragment

    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutBinding(binding: BookmarksFragmentBinding) {
        bookmarksFragmentBinding = binding
    }
}