package com.example.android.bake;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class RecyclerViewMatcher {

    final int recyclerViewId;

    private RecyclerViewMatcher(int recyclerViewId) {
        this.recyclerViewId = recyclerViewId;
    }

    public static RecyclerViewMatcher withRecyclerView(int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }

    public TypeSafeMatcher<View> atPosition(int position) {
        return atPositionOnView(position, -1);
    }

    TypeSafeMatcher<View> atPositionOnView(int position, int targetViewId) {
        return new TypeSafeMatcher<View>() {

            Resources resources;
            View childView;

            @Override
            public void describeTo(Description description) {
                String idDescription = Integer.toString(recyclerViewId);
                if (resources != null) {
                    try {
                        idDescription = resources.getResourceName(recyclerViewId);
                    } catch (Resources.NotFoundException e) {
                        idDescription = String.format("%s (resource name not found)", recyclerViewId);
                    }
                }
                description.appendText("with id: " + idDescription);
            }

            @Override
            protected boolean matchesSafely(View item) {
                resources = item.getResources();
                if (childView == null) {
                    RecyclerView recyclerView = item.getRootView().findViewById(recyclerViewId);
                    if (recyclerView.getId() == recyclerViewId) {
                        RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
                        if (viewHolder != null) {
                            childView = viewHolder.itemView;
                        }
                    } else {
                        return false;
                    }
                }

                if (targetViewId == -1) {
                    return item == childView;
                } else {
                    View targetView = childView.findViewById(targetViewId);
                    return item == targetView;
                }
            }
        };
    }
}
