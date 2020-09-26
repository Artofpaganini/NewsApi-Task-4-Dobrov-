package by.andersen.intern.dobrov.mynewsapi.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;

import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import by.andersen.intern.dobrov.mynewsapi.entity.Article;

public interface NewsListView extends MvpView {

    void swipe();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showError();

    void showData(List<Article> articles);
}
