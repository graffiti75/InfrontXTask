package br.android.cericatto.infrontxtask.adapter

import br.android.cericatto.infrontxtask.data.common.AwayTeam
import br.android.cericatto.infrontxtask.data.common.CompetitionStage
import br.android.cericatto.infrontxtask.data.common.HomeTeam
import br.android.cericatto.infrontxtask.data.common.Venue
import br.android.cericatto.infrontxtask.data.result.Score

sealed class ResultsRecyclerViewItem {

    class Title(
        val title: String? = ""
    ) : ResultsRecyclerViewItem()

    class Result(
        val awayTeam: AwayTeam? = AwayTeam(),
        val competitionStage: CompetitionStage? = CompetitionStage(),
        val date: String? = "",
        val homeTeam: HomeTeam? = HomeTeam(),
        val id: Int? = -1,
        val score: Score? = Score(),
        val venue: Venue? = Venue()
    ) : ResultsRecyclerViewItem()
}