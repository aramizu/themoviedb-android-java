package br.com.aramizu.themoviedb.presentation.ui.base;

/**
 * Interfac to interact with FragNavController
 */
public interface FragManagerListerner {
    void pushFragment(BaseFragment fragment);
    void popFragment();
}
