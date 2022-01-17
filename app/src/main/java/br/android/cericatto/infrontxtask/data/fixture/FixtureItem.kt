package br.android.cericatto.infrontxtask.data.fixture

import br.android.cericatto.infrontxtask.data.common.AwayTeam
import br.android.cericatto.infrontxtask.data.common.CompetitionStage
import br.android.cericatto.infrontxtask.data.common.HomeTeam
import br.android.cericatto.infrontxtask.data.common.Venue

data class FixtureItem(
    val awayTeam: AwayTeam? = AwayTeam(),
    val competitionStage: CompetitionStage? = CompetitionStage(),
    val date: String? = "",
    val homeTeam: HomeTeam? = HomeTeam(),
    val id: Int? = -1,
    val state: String? = "",
    val type: String? = "",
    val venue: Venue? = Venue()
)