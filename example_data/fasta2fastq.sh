#!/usr/bin/env bash

# Read FASTA files, and output a FASTQ formatted file, using a quality score of I for each base in the read

awk '
BEGIN{
  head = ""
  seq  = ""
}
{
  if(substr($0,1,1) == ">"){
    if(length(head) > 0){
      printf "@%s\n%s\n+\n", head, seq
      for(i = 0; i< length(seq); i++){ printf "I"}
      printf "\n"
    }
    head = substr($0,2,length($0))
    seq  = ""
  } else {
    printf seq
    seq = seq $0
  }
}

END{
  printf "@%s\n%s\n+\n", head, seq
  for(i = 0; i< length(seq); i++){ printf "I"}
  printf "\n"
}
'
