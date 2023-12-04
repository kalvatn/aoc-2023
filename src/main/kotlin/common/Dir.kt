package common

enum class Dir(val diff: Point2D) {
  N(Point2D(-1, 0)),
  E(Point2D(0, 1)),
  S(Point2D(1, 0)),
  W(Point2D(0, -1)),
  NE(N.diff + E.diff),
  NW(N.diff + W.diff),
  SE(S.diff + E.diff),
  SW(S.diff + W.diff)
}
