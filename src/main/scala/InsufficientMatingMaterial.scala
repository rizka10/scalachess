package chess

// http://www.e4ec.org/immr.html

object InsufficientMatingMaterial {

  def nonKingPieces(board: Board) = board.pieces.filter(_._2.role != King).toList

  def bishopsOnDifferentColor(board: Board) = {
    val notKingPieces = nonKingPieces(board)

    notKingPieces.map(_._2.role).distinct == List(Bishop) &&
      notKingPieces.map(_._1.color).distinct.size == 2
  }

  def apply(board: Board) = {

    lazy val notKingPieces = nonKingPieces(board)

    def kingsOnly = board.pieces.size < 3

    def bishopsOnSameColor =
      notKingPieces.map(_._2.role).distinct == List(Bishop) &&
        notKingPieces.map(_._1.color).distinct.size == 1

    def singleKnight = notKingPieces.map(_._2.role) == List(Knight)

    kingsOnly || bishopsOnSameColor || singleKnight
  }

  def apply(board: Board, color: Color) =
    board rolesOf color filter (King !=) match {
      case Nil | List(Knight) | List(Bishop) => true
      case _                                 => false
    }
}
