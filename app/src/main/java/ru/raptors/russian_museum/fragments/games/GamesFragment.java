package ru.raptors.russian_museum.fragments.games;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import ru.raptors.russian_museum.R;
import ru.raptors.russian_museum.databinding.FragmentGamesBinding;

public class GamesFragment extends Fragment {

    private FragmentGamesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGamesBinding.inflate(inflater, container, false);
        binding.toFindObject.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.
                action_navigation_dashboard_to_findObjectActivity));
        binding.toPuzzles.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.
                action_navigation_games_to_puzzlesActivity));
        binding.toGuessGenre.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.
                action_navigation_games_to_guessGenreChooseActivity));
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}