/******************************************************************************
 * Determine Unique sequence identifiers based on on two inputs
 * 
 *****************************************************************************/

package hse

//import hse.Fasta
//import hse.Utils

/*****************************************************************************/

object kmerTools {

  def usage(arg0: String): Unit = {
    println("Unique identifier detection")
    println("Usage: " + arg0 + " <sequences> <genomes>");
  }


/*****************************************************************************/

  /*****************************************************************************/

  def allKmers(seq: String, k: Int): Set[String] = {
    /***********************************************
     * Return all the Kmers in a string seq, given a size of k
     **********************************************/

    val upst_downst = ((k.toDouble - 1.0) / 2.0).toInt
    val i_locations = ((upst_downst) until (seq.length() - upst_downst)).toList
 
    def kmer_at_i(seq: String, i: Int): String = {
      seq.substring(i-upst_downst, i+upst_downst+1)
    }

    i_locations.map( (i: Int) => kmer_at_i(seq, i)).toSet

  }

/*****************************************************************************/

  def fuckuprint(L: List[(Set[String],Int)]) = {

    println("printing fuck++++");
    for (fuck <- L) {
      println(fuck._2)
      printSet(fuck._1);
    }
   println("++++++")
  }

  def printSet( S: Set[String]) = {
    println("---");
    for (elem <- S){
      println(elem)
    }
    println("-----");
  }

  def uniqueKmers(kmers: List[Set[String]]): List[Set[String]] = {
    /**************************************************************
     * Given a list of outputs from allKmers, return only those which are unique to one of them
     *************************************************************/
     val kmer_index : List[(Set[String],Int)]   = kmers.zipWithIndex;
     val remove_index = (L: List[(Set[String],Int)], index: Int) =>(L.patch(index, Nil, 1))
     
     def setUnion(L: List[Set[String]]): Set[String] = {
       var U = L(0);
       L.fold(U){ (a,b) => a.union(b) }
       //for ( s <- L ) {
       //  U = U.union(s);
       //}
       //println("Union");
       //printSet(U);
       //return U;
     }
     kmer_index.map(elem => elem._1.diff(setUnion(remove_index(kmer_index, elem._2).map(vi => vi._1))))
  }

/*****************************************************************************/

  def orthologKmers(orthologs: List[Fasta.Entry], k: Int) : List[Any] = {
    
    val orthologNames = orthologs.map(o => o.description);
    val orthologSeqs  = orthologs.map(o => o.sequence).toList;

    Utils.suckLessZip(List(orthologNames, (uniqueKmers(orthologSeqs.map( s => allKmers(s,k))))))

  }


/*****************************************************************************/

}