package com.example.dclassicsbooks.adapters;

import android.content.Intent;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.PathInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dclassicsbooks.R;
import com.example.dclassicsbooks.activities.BooksDetailsActivity;
import com.example.dclassicsbooks.models.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BookCatalogAdapter extends RecyclerView.Adapter<BookCatalogAdapter.BookCatalogViewHolder> {
    private final List<Book> visibleBooks = new ArrayList<>();
    private int expandedPosition = RecyclerView.NO_POSITION;

    public BookCatalogAdapter(List<Book> books) {
        visibleBooks.addAll(books);
    }

    public void submitList(List<Book> books) {
        visibleBooks.clear();
        visibleBooks.addAll(books);
        expandedPosition = RecyclerView.NO_POSITION;
        notifyDataSetChanged();
    }

    @NonNull @Override
    public BookCatalogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_catalog, parent, false);
        return new BookCatalogViewHolder(view);
    }

    @Override public void onBindViewHolder(@NonNull BookCatalogViewHolder holder, int position) {
        Book book = visibleBooks.get(position);
        holder.image.setImageResource(book.getImage());
        holder.title.setText(book.getTitle());
        holder.author.setText(book.getAuthor());
        holder.category.setText(book.getCategory());
        holder.rating.setText(String.format(Locale.getDefault(), "\u2605 %.1f / 5.0", book.getRating()));
        boolean expanded = position == expandedPosition;
        int panelWidth = panelWidth(holder, expanded);
        holder.actionPanel.setLayoutParams(new LinearLayout.LayoutParams(panelWidth, ViewGroup.LayoutParams.MATCH_PARENT));
        holder.actionButtons.animate().cancel();
        holder.actionButtons.animate().setListener(null);
        holder.actionButtons.setVisibility(expanded ? View.VISIBLE : View.GONE);
        holder.actionButtons.setAlpha(expanded ? 1f : 0f);
        holder.arrow.setImageResource(expanded ? R.drawable.ic_catalog_chevron_left : R.drawable.ic_catalog_chevron_right);

        View.OnClickListener openDetails = v -> {
            Intent intent = new Intent(v.getContext(), BooksDetailsActivity.class);
            intent.putExtra("book_title", book.getTitle());
            intent.putExtra("book_image", book.getImage());
            v.getContext().startActivity(intent);
        };
        holder.detailButton.setOnClickListener(openDetails);
        holder.orderButton.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), BooksDetailsActivity.class);
            intent.putExtra("book_title", book.getTitle());
            intent.putExtra("book_image", book.getImage());
            intent.putExtra("open_order", true);
            v.getContext().startActivity(intent);
        });
        holder.arrow.setOnClickListener(v -> {
            int oldPosition = expandedPosition;
            int clickedPosition = holder.getBindingAdapterPosition();
            if (clickedPosition == RecyclerView.NO_POSITION) return;
            boolean expand = clickedPosition != expandedPosition;
            expandedPosition = expand ? clickedPosition : RecyclerView.NO_POSITION;
            if (oldPosition != RecyclerView.NO_POSITION && oldPosition != clickedPosition) {
                notifyItemChanged(oldPosition);
            }
            animateActionPanel(holder, expand);
        });
    }

    private int panelWidth(BookCatalogViewHolder holder, boolean expanded) {
        float density = holder.itemView.getResources().getDisplayMetrics().density;
        return (int) ((expanded ? 130 : 48) * density);
    }

    private void animateActionPanel(BookCatalogViewHolder holder, boolean expand) {
        int startWidth = panelWidth(holder, !expand);
        int endWidth = panelWidth(holder, expand);
        holder.arrow.setImageResource(expand ? R.drawable.ic_catalog_chevron_left : R.drawable.ic_catalog_chevron_right);
        holder.actionButtons.animate().cancel();
        holder.actionButtons.animate().setListener(null);
        if (expand) {
            holder.actionButtons.setVisibility(View.VISIBLE);
            holder.actionButtons.setAlpha(0f);
            holder.actionButtons.animate().alpha(1f).setStartDelay(130).setDuration(180).start();
        } else {
            holder.actionButtons.animate().alpha(0f).setDuration(110).setListener(new AnimatorListenerAdapter() {
                @Override public void onAnimationEnd(android.animation.Animator animation) {
                    // Keep the space during the width animation so the chevron does not jump.
                    holder.actionButtons.setVisibility(View.INVISIBLE);
                    holder.actionButtons.setAlpha(0f);
                }
            }).start();
        }
        ValueAnimator widthAnimator = ValueAnimator.ofInt(startWidth, endWidth);
        widthAnimator.setDuration(360);
        widthAnimator.setInterpolator(new PathInterpolator(0.2f, 0f, 0f, 1f));
        widthAnimator.addUpdateListener(animation -> {
            holder.actionPanel.setLayoutParams(new LinearLayout.LayoutParams((int) animation.getAnimatedValue(), ViewGroup.LayoutParams.MATCH_PARENT));
            holder.itemView.requestLayout();
        });
        widthAnimator.addListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(android.animation.Animator animation) {
                if (!expand) {
                    holder.actionButtons.setVisibility(View.GONE);
                }
            }
        });
        widthAnimator.start();
    }

    @Override public int getItemCount() { return visibleBooks.size(); }

    static class BookCatalogViewHolder extends RecyclerView.ViewHolder {
        final ImageView image, arrow;
        final TextView title, author, category, rating;
        final LinearLayout actionPanel, actionButtons;
        final View detailButton, orderButton;
        BookCatalogViewHolder(@NonNull View view) {
            super(view);
            image = view.findViewById(R.id.bookCatalogImage);
            arrow = view.findViewById(R.id.bookCatalogArrow);
            title = view.findViewById(R.id.bookCatalogTitle);
            author = view.findViewById(R.id.bookCatalogAuthor);
            category = view.findViewById(R.id.bookCatalogCategory);
            rating = view.findViewById(R.id.bookCatalogRating);
            actionPanel = view.findViewById(R.id.bookActionPanel);
            actionButtons = view.findViewById(R.id.bookActionButtons);
            detailButton = view.findViewById(R.id.btnBookDetail);
            orderButton = view.findViewById(R.id.btnBookOrder);
        }
    }
}
