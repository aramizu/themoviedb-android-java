package br.com.aramizu.themoviedb.presentation.ui.base;

public interface FragManagerListerner {
    void pushFragment(BaseFragment fragment);
    void popFragment();
}
