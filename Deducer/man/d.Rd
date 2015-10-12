\name{d}
\alias{d}
\title{
  wrapper for data.frame
}
\description{
  This function creates data frames, tightly coupled collections of
     variables which share many of the properties of matrices and of
     lists, used as the fundamental data structure by most of R's
     modeling software. It is a keystroke saving wrapper for the data.frame function.
     The only difference is that check.names and stringsAsFactors are FALSE by default.
}
\usage{
d(..., row.names = NULL, check.rows = FALSE,
                check.names = FALSE,
                stringsAsFactors = FALSE)
}
\arguments{
     \item{...}{ items}

\item{row.names}{NULL or a single integer or character string
          specifying a column to be used as row names, or a character
          or integer vector giving the row names for the data frame.}

\item{check.rows}{ if TRUE then the rows are checked for consistency of
          length and names.}

\item{check.names}{ logical.  If TRUE then the names of the variables in
          the data frame are checked to ensure that they are
          syntactically valid variable names and are not duplicated.
          If necessary they are adjusted (by make.names) so that
          they are.}

\item{stringsAsFactors}{ logical: should character vectors be converted to
          factors? }
}
\seealso{
  \code{\link{data.frame}}
}
\examples{
x <- d(rnorm(10),1:10)
} 