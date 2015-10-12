\name{get.objects}
\alias{get.objects}
\title{
  get objects
}
\description{
  Enumerates all objects of a certain class
}
\usage{
get.objects(cn,env = globalenv(),includeInherited=TRUE)
}
\arguments{
  \item{cn}{The name of the class}
  \item{env}{environment to look in}
  \item{includeInherited}{Should objects inheriting cn be included}
}
\value{
a character vector
}
