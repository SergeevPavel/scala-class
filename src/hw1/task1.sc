
def pathsCountFunctional(N: Int, K: Int): Int = {
  N match {
    case N if N < 0 => 0
    case 0 => 1
    case N => (1 to K).map((k) => pathsCountFunctional(N - k, K)).sum
  }
}

def pathsCountImperative(N: Int, K: Int): Int = {
  val data = Array.fill(N + 1)(0)
  data(0) = 1
  for {
    i <- 1 to N
    j <- 1 to K
    if i - j >= 0
  } data(i) += data(i - j)
  data(N)
}

def pathsCount(N: Int, K: Int): Int = {
  val data = Array.fill(N + 1)(0)
  data(0) = 1
  for {
    i <- 1 to N
  } data(i) = data.view(math.max(i - K, 0), i).sum
  data(N)
}

def pathsCount1(N: Int, K: Int): Int = {
  def go(lst: List[Int]) = lst.sum :: lst.take(K - 1)
  Iterator.iterate(1 :: List.fill(K - 1)(0))(go).next().head
}

pathsCount(1, 10)
pathsCount(4, 2)
pathsCount(2, 1)

pathsCountImperative(1, 10)
pathsCountImperative(4, 2)
pathsCountImperative(2, 1)

pathsCountFunctional(1, 10)
pathsCountFunctional(4, 2)
pathsCountFunctional(2, 1)