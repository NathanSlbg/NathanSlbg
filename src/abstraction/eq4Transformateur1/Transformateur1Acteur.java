package abstraction.eq4Transformateur1;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.filiere.IActeur;
import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.general.Variable;
import abstraction.eqXRomu.general.VariablePrivee;
import abstraction.eqXRomu.general.VariableReadOnly;
import abstraction.eqXRomu.produits.Feve;

public class Transformateur1Acteur implements IActeur {
	public static Color COLOR_LLGRAY = new Color(238,238,238);
	public static Color COLOR_BROWN  = new Color(141,100,  7);
	public static Color COLOR_PURPLE = new Color(100, 10,115);
	public static Color COLOR_LPURPLE= new Color(155, 89,182);
	public static Color COLOR_GREEN  = new Color(  6,162, 37);
	public static Color COLOR_LGREEN = new Color(  6,255, 37);
	public static Color COLOR_LBLUE = new Color(  6,130,230);
	
	protected Integer cryptogramme;
	protected Journal journal;

	private Variable qualiteHaute;  // La qualite d'un chocolat de gamme haute 
	private Variable qualiteMoyenne;// La qualite d'un chocolat de gamme moyenne  
	private Variable qualiteBasse;  // La qualite d'un chocolat de gamme basse
	private Variable gainQualiteBioEquitable;// Le gain en qualite des chocolats bio equitables
	private Variable gainQualiteOriginal;// Le gain en qualite des chocolats originaux
	private Variable partMarqueQualitePercue;// Le gain en qualite des chocolats originaux
	private Variable pourcentageMinCacaoBQ; //Le pourcentage minimal de cacao dans un chocolat de basse qualite
	private Variable pourcentageMinCacaoMQ; //Le pourcentage minimal de cacao dans un chocolat de moyenne qualite
	private Variable pourcentageMinCacaoHQ; //Le pourcentage minimal de cacao dans un chocolat de haute qualite
	private Variable partCacaoQualitePercue ;//L'impact d'un % de cacao plus eleve dans la qualite percue du chocolat
	private Variable pourcentageRSEmax;//Le pourcentage de reversion RSE pour un impact max sur la qualite percue
	private Variable partRSEQualitePercue;//L'impact de pourcentageRSEmax% du prix consacres aux RSE dans la qualite percue du chocolat
	private Variable coutStockageProducteur;//Le cout moyen du stockage d'une Tonne a chaque step chez un producteur de feves

	protected Variable totalStocksFeves;  // La qualite totale de stock de feves 
	protected Variable totalStocksChoco;  // La qualite totale de stock de chocolat 
	protected Variable totalStocksChocoMarque;  // La qualite totale de stock de chocolat de marque 
	protected List<Feve> lesFeves;

	public Transformateur1Acteur() {
		this.qualiteHaute   = new VariableReadOnly("qualite haute", "<html>Qualite du chocolat<br>de gamme haute</html>",this, 0.0, 10.0, 3.0);
		this.qualiteMoyenne = new VariableReadOnly("qualite moyenne", "<html>Qualite du chocolat<br>de gamme moyenne</html>",this, 0.0, 10.0, 2.0);
		this.qualiteBasse   = new VariableReadOnly("qualite basse", "<html>Qualite du chocolat<br>de gamme basse</html>",this, 0.0, 10.0, 1.0);
		this.gainQualiteBioEquitable  = new VariableReadOnly("gain qualite bioequitable", "<html>Gain en qualite des<br>chocolats bio equitables</html>",this, 0.0, 5.0, 0.5);
		this.gainQualiteOriginal  = new VariableReadOnly("gain qualite original", "<html>Gain en qualite des<br>chocolats originaux</html>",this, 0.0, 5.0, 0.5);
		this.partMarqueQualitePercue  = new VariableReadOnly("impact marque qualite percue", "<html>% de la qualite percue de la marque dans la qualite percue du chocolat</html>",this, 0.0, 0.5, 0.3);

		this.pourcentageMinCacaoBQ  = new VariableReadOnly("pourcentage min cacao BQ", "<html>Le pourcentage minimal de cacao dans un chocolat de basse qualite</html>",this, 30.0, 45.0, 40.0);
		this.pourcentageMinCacaoMQ  = new VariableReadOnly("pourcentage min cacao MQ", "<html>Le pourcentage minimal de cacao dans un chocolat de moyenne qualite</html>",this, 45.0, 60.0, 60.0);
		this.pourcentageMinCacaoHQ  = new VariableReadOnly("pourcentage min cacao HQ", "<html>Le pourcentage minimal de cacao dans un chocolat de haute qualite</html>",this, 60.0, 90.0, 80.0);
		this.partCacaoQualitePercue = new VariableReadOnly("impact cacao qualite percue", "<html>L'impact d'un % de cacao plus eleve dans la qualite percue du chocolat</html>",this, 0.0, 0.5, 0.3);
		
		this.pourcentageRSEmax    = new VariableReadOnly("pourcentage rse max", "<html>Le pourcentage de reversion RSE pour un impact max sur la qualite percue</html>",this, 5.0, 30.0, 20.0);
		this.partRSEQualitePercue = new VariableReadOnly("impact rse qualite percue", "<html>L'impact de 25% du prix consacres aux RSE dans la qualite percue du chocolat</html>",this, 0.0, 0.5, 0.3);

		this.coutStockageProducteur = new VariableReadOnly("cout moyen stockage producteur", "<html>Le cout moyen du stockage d'une Tomme de produit chez un producteur</html>",this, 0.0, 3.0, 1.5);
		this.journal = new Journal("Journal "+this.getNom(), this);
		this.totalStocksFeves = new VariablePrivee("EqXStockFeves", "<html>Quantite totale de feves en stock</html>",this, 0.0, 1000000.0, 0.0);
		this.totalStocksChoco = new VariablePrivee("EqXStockChoco", "<html>Quantite totale de chocolat en stock</html>",this, 0.0, 1000000.0, 0.0);
		this.totalStocksChocoMarque = new VariablePrivee("EqXStockChocoMarque", "<html>Quantite totale de chocolat de marque en stock</html>",this, 0.0, 1000000.0, 0.0);
	}
	
	public void initialiser() {
	}

	public String getNom() {// NE PAS MODIFIER
		return "EQ4";
	}

	////////////////////////////////////////////////////////
	//         En lien avec l'interface graphique         //
	////////////////////////////////////////////////////////

	public void next() {
	}

	public Color getColor() {// NE PAS MODIFIER
		return new Color(229, 243, 157); 
	}

	public String getDescription() {
		return "Bla bla bla";
	}

	// Renvoie les indicateurs
	public List<Variable> getIndicateurs() {
		List<Variable> res = new ArrayList<Variable>();
		return res;
	}

	// Renvoie les parametres
	public List<Variable> getParametres() {
		List<Variable> res=new ArrayList<Variable>();
		return res;
	}

	// Renvoie les journaux
	public List<Journal> getJournaux() {
		List<Journal> res=new ArrayList<Journal>();
		return res;
	}

	////////////////////////////////////////////////////////
	//               En lien avec la Banque               //
	////////////////////////////////////////////////////////

	// Appelee en debut de simulation pour vous communiquer 
	// votre cryptogramme personnel, indispensable pour les
	// transactions.
	public void setCryptogramme(Integer crypto) {
		this.cryptogramme = crypto;
	}

	// Appelee lorsqu'un acteur fait faillite (potentiellement vous)
	// afin de vous en informer.
	public void notificationFaillite(IActeur acteur) {
	}

	// Apres chaque operation sur votre compte bancaire, cette
	// operation est appelee pour vous en informer
	public void notificationOperationBancaire(double montant) {
	}
	
	// Renvoie le solde actuel de l'acteur
	public double getSolde() {
		return Filiere.LA_FILIERE.getBanque().getSolde(Filiere.LA_FILIERE.getActeur(getNom()), this.cryptogramme);
	}

	////////////////////////////////////////////////////////
	//        Pour la creation de filieres de test        //
	////////////////////////////////////////////////////////

	// Renvoie la liste des filieres proposees par l'acteur
	public List<String> getNomsFilieresProposees() {
		ArrayList<String> filieres = new ArrayList<String>();
		return(filieres);
	}

	// Renvoie une instance d'une filiere d'apres son nom
	public Filiere getFiliere(String nom) {
		return Filiere.LA_FILIERE;
	}

}
