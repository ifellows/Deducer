\name{perm}
\alias{perm}
\title{
  Vector Permutations
}
\description{
  Enumerates all permutations of a vector
}
\usage{
perm(vec,duplicates=FALSE)
}
\arguments{
  \item{vec}{The vector to permute}
  \item{duplicates}{Should duplicate permutations be listed}
}
\value{
  Returns a matrix where each row is a permutation of vec. All possible permutations are listed, and if \code{duplicates=TRUE} non-unique permutations are also listed.
}
\seealso{
  \code{\link{sample}}
}
\examples{
perm(1:4)
perm(LETTERS[4:8])

}