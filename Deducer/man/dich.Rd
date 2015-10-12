\name{dich}
\alias{dich}
\title{dich}
\description{
  splits a variable into two groups
}
\usage{
dich(variables,data=NULL,cut=NULL,group1=NULL,group2=NULL)
}
\arguments{
  \item{variables}{variables to be dichotomized}
  \item{data}{A data.frame}
  \item{cut}{An optional cut point dividing \code{factor} into two groups.}
  \item{group1}{An optional vector of levels of \code{factor} defining group 1.}
  \item{group2}{An optional vector of levels of \code{factor} defining group 2.}
}
\value{
  a data.frame containing the variables, recoded into two groups.
} 