package br.android.cericatto.infrontxtask.adapter

import br.android.cericatto.infrontxtask.data.common.AwayTeam
import br.android.cericatto.infrontxtask.data.common.CompetitionStage
import br.android.cericatto.infrontxtask.data.common.HomeTeam
import br.android.cericatto.infrontxtask.data.common.Venue

sealed class FixturesRecyclerViewItem {

    class Title(
        val title: String
    ) : FixturesRecyclerViewItem()

    class Fixture(
        val awayTeam: AwayTeam,
        val competitionStage: CompetitionStage,
        val date: String,
        val homeTeam: HomeTeam,
        val id: Int,
        val state: String? = "",
        val venue: Venue? = Venue()
    ) : FixturesRecyclerViewItem()
}