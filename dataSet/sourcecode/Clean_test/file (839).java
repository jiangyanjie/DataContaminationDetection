import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Creates tables for GTMRS in the mySQL database.
 * @author Annette Almonte Malagon
 */
public class CreateTables {
	
	private DBConnector dbc;
	private static Connection conn;
	private Statement stmt;
	
	private String[] users = {"'jconley', 'apple'", "'mwilliams', 'banana'", "'sward', 'orange8'", "'jlovato', 't0mat0'",
								"'mhall3', 'carrot9'", "'cyoung', 'lasagna29'", "'phumming', 'beans'", "'wphong', 'corn890'",
								"'apuck', 'strawberry'", "'ltorres', 'cherry'", "'cmartin', 'blueberry'", 
								"'jcates', 'raspberry'", "'nmartell', 'turk3y'", "'smartinez', 'p@st@'", "'jmaine', 'saucey'",
								"'rtrap', 'daikon'", "'hwood', 'eggplant'", "'swright', 'lettuce'", "'aanderson', 'asparagus'",
								"'ysmith', 'grape'", "'acook', 'lem0n'", "'sholmes', 'lime44'", "'abloom', 'chicken'", 
								"'bhunt', 'potato'", "'tsmith', 'radish3s'", "'evanmae', '0ni0n'", "'gwells', 'couscous'",
								"'vpalacio', 'anan@'", "'vayo3', 'ste@k42'"
	};
	private String[] patients = {"'jconley', 'Jonathan Conley', '1980-07-30', 'm', '330577 Georgia Tech Station, Atlanta, GA 30332', '141-300-3976', '440-806-5874', 'Melvin Abbott', '440-661-8089', 178, '5ft8in', '25000-50000', '5478-4802-9258-4310'",
			"'mwilliams', 'Maryam Williams', '1968-07-03', 'f',	'899 Peachtree Street, Atlanta, GA 30332', '271-925-4614', '440-675-3454', 'Stuart Williams', '770-964-4954', 145, '5ft7in', '50000-100000', '5377-0281-7667-4960'",
			"'sward', 'Samantha Ward', '1989-01-09', 'f', '330288 Georgia Tech Station, Atlanta, GA 30332',	'851-519-5436',	'440-490-7167',	'Dorothy Ward', '612-944-7579',	123, '5ft3in', '0-25000', '5380-6414-0739-7370'",
			"'jlovato', 'Julianne Lovato', '1992-09-25', 'f', '330126 Georgia Tech Station, Atlanta, GA 30333', '440-185-1901', '440-780-6494', 'Benjamin Green', '628-593-0487', 115, '5ft1in', '0-25000', '5420-3955-0147-3920'",
			"'mhall3', 'Michelle Hall', '1994-05-08', 'f', '330211 Georgia Tech Station, Atlanta, GA 30334', '440-158-4412', '440-182-6210', 'Muriel Lenon', '454-617-5721', 162, '5ft9in' '0-25000', '5228-4734-5821-6420'",
			"'cyoung', 'Curt Young', '1990-10-13', 'm', '330390 Georgia Tech Station, Atlanta, GA 30335', '440-726-4194', '440-878-2732', 'Rama Young', '440-737-6441', 193, 6ft2in', '0-25000', '5377-5416-5866-6980'",
			"'phumming', 'Phineas Humming', '1990-09-14', 'm', '330467 Georgia Tech Station, Atlanta, GA 30336', '440-193-8440', '440-618-7465', 'Karen Humming', '440-613-4860', 136, '5ft4in', '0-25000', '5484-2117-0084-6280'",
			"'wphong', 'Wu Phong', '1991-09-23', 'm', '330111 Georgia Tech Station, Atlanta, GA 30337', '770-782-2565', '440-181-3486', 'Colt Smith', '770-694-6690', 140, '5ft6in', '0-25000', '5110-0054-3678-2290'",
			"'apuck', 'Aidan Puck',	'1988-03-14', 'm', '330400 Georgia Tech Station, Atlanta, GA 30338', '816-160-3930', '440-977-8499', 'Sydney Puck',	'816-534-8133',	214, '6ft7in', '0-25000', '5130-8754-7071-1310'",
			"'ltorres',	'Lucas Torres',	'1994-04-06', 'm', '330267 Georgia Tech Station, Atlanta, GA 30339', '816-416-5370', '440-952-3630', 'Owen Torres',	'816-631-6511',	167, '5ft8in', '0-25000', '5447-1964-4265-0510'"
			
	};
	private String[] doctors = {"'cmartin', '918694500', 'Claire', 'Martin', '1951-12-04', '404-701-6363', 'General Physician', 218, '2300 Windy Ridge Pkwy SE, Atlanta, GA 30339'",
			"'jcates', '894614673', 'Jessica', 'Cates', '1966-12-04', '404-297-1223', 'General Physician', 229,	'3207 Post Woods Dr, Atlanta, GA 30339'",
			"'nmartell', '817833422', 'Natalie', 'Martell', '1973-06-10', '404-252-1791', 'General Physician', 342,	'2168 Penrose Dr, Atlanta, GA 30344'",
			"'smartinez', '593831189', 'Stephen', 'Martinez', '1975-09-28', '404-271-6013', 'Heart Specialist', 113, '2637 Old Hapeville Rd SW, Atlanta, GA 30315'",
			"'jmaine', '821049088', 'Jason', 'Maine', '1971-12-01', '404-961-9213', 'Heart Specialist', 119, '2878 Gresham Rd SE, Atlanta, GA 30316'",
			"'rtrap', '142142209', 'Robert', 'Trap', '1967-12-06', '404-857-4255', 'Heart Specialist', 367,	'3809 Castlegate Dr NW, Atlanta, GA 30327'",
			"'hwood', '259693250', 'Hannah', 'Wood', '1973-10-31', '404-551-6028', 'Eye Physician', 202, '222 12th St NE #1608, Atlanta, GA 30309'",
			"'swright', '957780987', 'Sully', 'Wright', '1958-04-02', '404-239-1275', 'Eye Physician', 	104, '2435 Northlake Ct NE, Atlanta, GA 30345'",
			"'aanderson', '334893865', 'Alphonse', 'Anderson', '1970-04-15', '404-961-5875', 'Eye Physician', 212, '3258 Pebble Dr, Atlanta, GA 30344'",
			"'ysmith', '112305674', 'Yune', 'Smith', '1959-06-09', '404-587-8371', 'Orthopedics', 355, '107 E Wesley Rd NE, Atlanta, GA 30305'",
			"'acook', '449628679', 'Amanda', 'Cook', '1952-02-26', '404-294-6385', 'Orthopedics', 104, '3633 Ingledale Dr SW, Atlanta, GA 30331'",
			"'sholmes', '421569030', 'Shadi', 'Holmes', '1970-08-20', '404-401-7384', 'Orthopedics', 138, '105 Glenlake Commons Dr, Decatur, GA 30030'",
			"'abloom', '309632171', 'Archer', 'Bloom', '1957-01-06','404-777-9481', 'Psychiatry', 130, '3070 Redwine Rd, Atlanta, GA 30344'",
			"'bhunt', '276546611', 'Brandon', 'Hunt', '1979-08-01',	'404-153-3327', 'Psychiatry', 340, '3664 Platina Park Ct, Atlanta, GA 30034'",
			"'tsmith', '223764432', 'Thomas', 'Smith', '1974-09-15', '404-675-3059', 'Psychiatry', 338, '222 12th St NE #3212, Atlanta, GA 30309'",
			"'evanmae', '256790965', 'Elizabeth', 'Vanmaele', '1980-10-03',	'404-922-5485', 'Gynecologist', 221, '644 Northumberland Dr, Alpharetta, GA 30004'",
			"'gwells', '765474273', 'George', 'Wells', '1970-02-22', '404-962-1039', 'Gynecologist', 217, '3201 Lenox Rd NE #15, Atlanta, GA 30324'",
			"'vpalacio', '643223226', 'Victoria', 'Palacio', '1959-12-18', '404-622-6508', 'Gynecologist', 105,	'241 Lake Forrest Ln NE, Atlanta, GA 30342'"
	};
	
	private String[] admins = {"'vayo3'"};
	
	private String[] requestAppt = {"'cmartin', 'jlovato', '2014-04-28', '9:30:00', '10:00:00'",
			"'abloom', 'apuck', '2014-04-29', '11:00:00', '11:30:00'",
			"'evanmae', 'ltorres', '2014-04-28', '13:00:00', '13:30:00'"	
	};
	
	private String[] visits = {"'jcates', 'mhall3', '2014-01-21', 160.00, 81, 122",
			"'jcates', 'mhall3', '2014-02-22', 520.00, 70, 100",
			"'jcates', 'mhall3', '2014-03-16', 120.00, 71, 103",
			"'aanderson', 'jlovato', '2014-01-06', 300.00, 75, 119",
			"'aanderson', 'jlovato', '2014-01-13', 120.00, 79, 118",
			"'aanderson', 'wphong', '2014-04-18', 160.00, 80, 118",
			"'abloom', 'jlovato', '2014-03-03',	120.00,	78,	115",
			"'abloom', 'jlovato', '2014-03-10',	120.00,	80,	112",
			"'abloom', 'jlovato', '2014-03-17',	595.00,	76,	110",
			"'acook', 'cyoung', '2014-02-03', 370.00, 73, 100",
			"'acook', 'cyoung', '2014-02-25', 370.00, 77, 101",
			"'acook', 'cyoung', '2014-03-25', 120.00, 75, 100",
			"'bhunt', 'ltorres', '2014-01-13', 160.00, 81, 120",
			"'bhunt', 'ltorres', '2014-01-28', 620.00, 89, 125",
			"'bhunt', 'mhall3', '2014-01-22', 120.00, 72, 104",
			"'cmartin', 'cyoung', '2014-03-07',	160.00,	73,	115",
			"'cmartin', 'jconley', '2014-01-13', 200.00, 70, 105",
			"'cmartin', 'wphong', '2014-04-01',	120.00,	80,	120",
			"'evanmae', 'mhall3', '2014-02-22',	120.00,	75,	104",
			"'evanmae', 'mhall3', '2014-03-21',	585.00,	73,	105",
			"'evanmae', 'mhall3', '2014-04-18',	120.00,	74,	101",
			"'gwells', 'jlovato', '2014-02-19', 120.00,	74,	118",
			"'gwells', 'jlovato', '2014-03-12',	120.00,	75,	116",
			"'gwells', 'mwilliams', '2014-01-24', 200.00, 76, 117",
			"'hwood', 'jconley', '2014-04-18', 150.00, 74, 107",
			"'hwood', 'mhall3', '2014-04-11', 120.00, 73, 103",
			"'hwood', 'wphong', '2014-04-03', 160.00, 80, 118",
			"'jmaine', 'ltorres', '2014-02-09',	120.00,	74,	116",
			"'jmaine', 'mwilliams', '2014-02-04', 150.00, 79, 119",
			"'jmaine', 'sward', '2014-02-11', 760.00, 79, 120",
			"'nmartell', 'mhall3', '2014-03-06', 120.00, 71, 105",
			"'nmartell', 'phumming', '2014-02-28', 570.00, 79, 120",
			"'nmartell', 'phumming', '2014-04-08', 570.00, 80, 121",
			"'rtrap', 'ltorres', '2014-01-14', 120.00, 75, 121",
			"'rtrap', 'ltorres', '2014-02-14', 120.00, 73, 118",
			"'rtrap', 'mwilliams', '2014-03-25', 150.00, 79, 118",
			"'sholmes', 'mwilliams', '2014-01-22', 200.00, 80, 120",
			"'sholmes', 'mwilliams', '2014-02-21', 150.00, 75, 115",
			"'sholmes', 'mwilliams', '2014-03-21', 150.00, 75, 114",
			"'smartinez', 'apuck', '2014-03-02', 120.00, 74, 103",
			"'smartinez', 'mwilliams', '2014-01-25', 1350.00, 73, 122",
			"'smartinez', 'mwilliams', '2014-02-25', 670.00, 80, 117",
			"'swright', 'apuck', '2014-03-06', 475.00, 73, 105",
			"'swright', 'apuck', '2014-03-20', 475.00, 79, 103",
			"'swright', 'apuck', '2014-03-31', 120.00, 78, 107",
			"'tsmith', 'apuck', '2014-02-05', 160.00, 80, 110",
			"'tsmith', 'phumming', '2014-02-19', 160.00, 79, 118",
			"'tsmith', 'phumming', '2014-03-26', 620.00, 79, 119",
			"'vpalacio', 'mwilliams', '2014-03-07',	150.00,	75,	115",
			"'vpalacio', 'sward', '2014-03-20',	120.00,	76,	119",
			"'vpalacio', 'sward', '2014-04-03',	477.50,	78,	120",
			"'ysmith', 'mhall3', '2014-04-10', 120.00, 70, 101",
			"'ysmith', 'sward', '2014-04-01', 120.00, 78, 121",
			"'ysmith', 'sward', '2014-04-08', 380.00, 78, 118"			
	};
	
	private String[] paymentInfo = {"'5478-4802-9258-4310', 'Jonathan Conley', 'MasterCard', '2016-09-01', '657'",
			"'5377-0281-7667-4960', 'Maryam Williams', 'MasterCard', '2015-04-01', '432'",
			"'5380-6414-0739-7370', 'Samantha Ward', 'MasterCard', '2015-03-01', '224'",
			"'5420-3955-0147-3920', 'Julianne Lovato', 'MasterCard', '2016-02-01', '110'",
			"'5228-4734-5821-6420', 'Michelle Hall', 'MasterCard', '2018-02-01', '980'",
			"'5377-5416-5866-6980', 'Curt Young', 'MasterCard', '2015-01-01', '776'",
			"'5484-2117-0084-6280', 'Phineas Humming', 'MasterCard', '2016-06-01', '579'",
			"'5110-0054-3678-2290', 'Wu Phong', 'MasterCard', '2017-12-01', '742'",
			"'5130-8754-7071-1310', 'Aidan Puck', 'MasterCard', '2016-08-01', '328'",
			"'5447-1964-4265-0510', 'Lucas Torres', 'MasterCard', '2018-09-01', '907'"
	};
	
	private String[] prescriptions = {"'jcates', 'mhall3', '2014-02-22', 'Oxycodone 400mg', 2, 7, 'Take one tablet by mouth every 12 hours', 'y'",
			"'aanderson', 'jlovato', '2014-01-06', 'Acetaminophen 200mg', 2, 14, 'Take one tablet by mouth every 12 hours', 'y'",
			"'aanderson', 'jlovato', '2014-01-13', 'Ibuprofen 800mg', 3, 14, 'Take one tablet by mouth every 8 hours', 'y'",
			"'aanderson', 'wphong', '2014-04-18', 'Ibuprofen 800mg', 3,	14,	'Take one tablet by mouth every 8 hours', 'y'",
			"'abloom', 'jlovato', '2014-03-03',	'Neutontin 15mg', 1, 30, 'Take one tablet by mouth every 24 hours', 'y'",
			"'acook', 'cyoung', '2014-02-03', 'Oxycodone 400mg', 2,	7, 'Take one tablet by mouth every 12 hours', 'y'",
			"'bhunt', 'ltorres', '2014-01-13', 'Heparin 10mg', 1, 60, 'Take one tablet by mouth every 24 hours', 'y'",
			"'bhunt', 'mhall3', '2014-01-22', 'Heparin 10mg', 1, 60, 'Take one tablet by mouth every 24 hours', 'y'",
			"'evanmae', 'mhall3', '2014-02-22',	'Minocycline 100mg', 1,	30,	'Take one capsule by mouth once daily; take with full glass of water', 'y'",
			"'gwells', 'jlovato', '2014-03-12',	'Minocycline 100mg', 1,	30,	'Take one capsule by mouth once daily; take with full glass of water', 'y'",
			"'gwells', 'mwilliams', '2014-01-24', 'Minocycline 100mg', 1, 30, 'Take one capsule by mouth once daily; take with full glass of water', 'y'",
			"'hwood', 'jconley', '2014-04-18', 'Restasis 3mL', 3, 7, 'Apply 1-3 drops into both eyes every 8 hours', 'y'",
			"'hwood', 'mhall3', '2014-04-11', 'Restasis 3mL', 3, 8,	'Apply 1-3 drops into both eyes every 8 hours', 'y'",
			"'hwood', 'wphong', '2014-04-03', 'Restasis 3mL', 3, 9,	'Apply 1-3 drops into both eyes every 8 hours', 'y'",
			"'jmaine', 'ltorres', '2014-02-09',	'Ibuprofen 800mg', 3, 14, 'Take one tablet by mouth every 8 hours', 'y'",
			"'jmaine', 'mwilliams', '2014-02-04', 'Ibuprofen 800mg', 3,	14,	'Take one tablet by mouth every 8 hours', 'y'",
			"'jmaine', 'sward', '2014-02-11', 'Ibuprofen 800mg', 3,	20,	'Take one tablet by mouth every 8 hours', 'y'",
			"'nmartell', 'mhall3', '2014-03-06', 'Ibuprofen 800mg', 3, 14, 'Take one tablet by mouth every 8 hours', 'y'",
			"'nmartell', 'phumming', '2014-02-28', 'Ibuprofen 800mg', 3, 14, 'Take one tablet by mouth every 8 hours', 'y'",
			"'rtrap', 'ltorres', '2014-01-14', 'Oxycodone 400mg', 2, 8,	'Take one tablet by mouth every 12 hours', 'y'",
			"'rtrap', 'mwilliams', '2014-03-25', 'Oxycodone 400mg', 2, 9, 'Take one tablet by mouth every 12 hours', 'y'",
			"'sholmes', 'mwilliams', '2014-02-21', 'Oxycodone 400mg', 2, 7, 'Take one tablet by mouth every 12 hours', 'y'",
			"'smartinez', 'mwilliams', '2014-02-25', 'Oxycodone 400mg', 2, 7, 'Take one tablet by mouth every 12 hours', 'y'",
			"'swright', 'apuck', '2014-03-06', 'Oxycodone 400mg', 2, 7,	'Take one tablet by mouth every 12 hours', 'y'",
			"'swright', 'apuck', '2014-03-20', 'Oxycodone 400mg', 2, 7,	'Take one tablet by mouth every 12 hours', 'y'",
			"'tsmith', 'phumming', '2014-02-19', 'Heparin 8mg', 1, 60, 'Take one tablet by mouth every 24 hours', 'y'",
			"'tsmith', 'apuck', '2014-02-05', 'Heparin 8mg', 1,	60,	'Take one tablet by mouth every 24 hours', 'y'",
			"'vpalacio', 'mwilliams', '2014-03-07',	'Minocycline 100mg', 1,	30,	'Take one capsule by mouth once daily; take with full glass of water', 'y'",
			"'vpalacio', 'sward', '2014-03-20',	'Minocycline 100mg', 1,	30,	'Take one capsule by mouth once daily; take with full glass of water', 'y'",
			"'ysmith', 'mhall3', '2014-04-10', 'Minocycline 100mg', 1, 30, 'Take one capsule by mouth once daily; take with full glass of water', 'y'",
			"'ysmith', 'sward', '2014-04-01', 'Ibuprofen 800mg', 3,	14,	'Take one tablet by mouth every 8 hours', 'y'"
	};
	
	private String[] diagnoses = {"'jcates', 'mhall3', '2014-01-21', 'Flu'",
			"'aanderson', 'jlovato', '2014-01-13', 'Sprained muscle in lower back'",
			"'aanderson', 'wphong', '2014-04-18', 'Sprained muscle in wrist'",
			"'abloom', 'jlovato', '2014-03-03',	'Depression'",
			"'acook', 'cyoung', '2014-02-03', 'Sprained muscle in ankle'",
			"'bhunt', 'ltorres', '2014-01-13', 'High cholesterol'",
			"'bhunt', 'mhall3', '2014-01-22', 'High cholesterol'",
			"'evanmae', 'mhall3', '2014-02-22',	'Eye infection'",
			"'gwells', 'jlovato', '2014-03-12',	'Eye infection'",
			"'gwells', 'mwilliams', '2014-01-24', 'Eye infection'",
			"'hwood', 'jconley', '2014-04-18', 'Dry eyes'",
			"'hwood', 'mhall3', '2014-04-11', 'Dry eyes'",
			"'hwood', 'wphong', '2014-04-03', 'Dry eyes'",
			"'jmaine', 'ltorres', '2014-02-09',	'Severe headaches'",
			"'jmaine', 'mwilliams', '2014-02-04', 'Severe headaches'",
			"'jmaine', 'sward', '2014-02-11', 'Severe headaches'",
			"'nmartell', 'mhall3', '2014-03-06', 'Severe headaches'",
			"'nmartell', 'phumming', '2014-02-28', 'Severe headaches'",
			"'rtrap', 'ltorres', '2014-01-14','Severe muscle strain in lower back'",
			"'rtrap', 'mwilliams', '2014-03-25', 'Severe muscle strain in left leg'",
			"'sholmes', 'mwilliams', '2014-02-21', 'Muscle strain in left leg'",
			"'tsmith', 'phumming', '2014-02-19', 'High cholesterol'",
			"'tsmith', 'apuck', '2014-02-05', 'High cholesterol'",
			"'vpalacio', 'mwilliams', '2014-03-07',	'Ear infection'",
			"'vpalacio', 'sward', '2014-03-20',	'Ear infection'",
			"'ysmith', 'mhall3', '2014-04-10', 'Fungal infection'",
			"'ysmith', 'sward', '2014-04-01', 'Cluster headaches'"
			
	};
	
	private String[] surgeries = {"'31502', 'Trauma surgery: tracheostomy', 800.00",
			"'92920', 'Percutaneous transluminal coronary angioplasty', 520.00",
			"'67930', 'Eyelid repair surgery', 360.00",
			"'66999', 'Phototherapeutic keratectomy', 710.00",
			"'43641', 'Lasik refractive surgery', 710.00",
			"'49000', 'Exploratory laparotomy', 450.00",
			"'49010', 'Therapeutic laparotomy: peptic ulcer removal', 450.00",
			"'33510', 'Coronary artery bypass grafting', 1200.00",
			"'27580', 'Arthrodesis: knee surgery', 520.00",
			"'29891', 'Arthroscopy: ankle; excision of osteochondral defect', 500.00",
			"'29899', 'Arthroscopy: ankle; with ankle arthrodesis', 500.00",
			"'61490', 'Bilateral cingulotomy', 950.00",
			"'61720', 'Subcaudate tractotomy', 1000.00",
			"'58570', 'Laparoscopic hysterectomy', 930.00",
			"'58353', 'Endometrial ablation', 715.00"
	};
	
	private String[] preopMeds = {"'29891', 'Diazepam'",
			"'27580', 'Atropine'",
			"'66999', 'Gentamicin eyedrops'",
			"'92920', 'Nitroglycerin'",
			"'92920', 'Ticlopidine'",
			"'92920', 'Aspirin'",
			"'67930', 'Lorazepam'",
			"'49000', 'Lorazepam'",
			"'49010', 'Lorazepam'",
			"'33510', 'Nitroglycerin'",
			"'33510', 'Ticlopidine'",
			"'29899', 'Diazepam'",
			"'61490', 'Phenytoin'",
			"'61720', 'Phenobarbital'",
			"'61720', 'Carbamazepine'",
			"'58570', 'Antiemetic'",
			"'58353', 'Antiemetic'"
	};

	private String[] performs = {"'jcates', 'mhall3', '31502', 2, '9:00:00', '10:07:00', '8:45:00', 'None'",
			"'aanderson', 'jlovato', '67930', 1, '10:13:00', '10:57:00', '10:03:00', 'None'",
			"'smartinez', 'mwilliams', '92920', 1, '8:25:00', '11:14:01', '8:15:00', 'None'",
			"'swright', 'apuck', '67930', 2, '13:00:00', '14:37:00', '12:50:00', 'None'",
			"'swright', 'apuck', '66999', 1, '13:00:00', '14:58:01', '12:50:01', 'None'",
			"'nmartell', 'phumming', '49000', 2, '8:32:00', '11:57:00', '8:22:00', 'None'",
			"'nmartell', 'phumming', '49010', 2, '7:17:00', '9:32:00', '7:07:00', 'None'",
			"'smartinez', 'mwilliams', '33510', 3, '9:10:00', '11:47:00', '9:00:00', 'None'",
			"'jmaine', 'sward', '33510', 1,	'1:10:00', '2:14:00', '1:00:00', 'None'",
			"'ysmith', 'sward', '27580', 1,	'3:17:00', '4:03:00', '3:07:00', 'None'",
			"'acook', 'cyoung', '29891', 2,	'11:27:00', '13:19:00', '11:17:00', 'None'",
			"'acook', 'cyoung', '29899', 2,	'9:07:00', '10:17:00', '8:57:00', 'None'",
			"'abloom', 'jlovato', '61490', 2, '8:30:00', '10:47:00', '8:20:00', 'None'",
			"'bhunt', 'ltorres', '61720', 2, '8:30:00', '10:31:00', '8:20:01', 'None'",
			"'tsmith', 'phumming', '61720', 2, '8:13:00', '10:05:00', '8:03:00', 'None'",
			"'evanmae', 'mhall3', '58570', 1, '8:24:00', '9:45:00', '8:14:00', 'None'",
			"'gwells', 'jlovato', '58570', 1, '13:14:00', '14:39:00', '13:04:00', 'None'",
			"'vpalacio', 'sward', '58353', 1, '15:22:00', '16:55:00', '15:09:00', 'None'"	
	};
	
	private String[] messagesToDoc = {"'aanderson', 'wphong', '2014-04-18 23:11:00', 'Hi Dr. Anderson: Am I supposed to take the ibuprofen after dinner?', 'u'",
			"'acook', 	'cyoung', 	'2014-02-05 18:32:00',	'Dr. Cook: Is it alright for me to use my regular eye drops?', 	'r'",
			"'smartinez', 'mwilliams', '2014-02-22 1:05:00', 'Dear Dr. Martinez: Is there anything else I need to do in order to prepare for my upcoming surgery?', 'r'"
	};
	
	private String[] messagesToPatient = {"'aanderson', 'wphong', '2014-04-18 23:11:00', 'You do not have to take the medication with a meal unless it upsets your stomach (if you take it on an empty stomach).', 'u'",
			"'acook', 'cyoung', '2014-02-05 23:05:00', 'Yes--that is fine.', 'r'",
			"'smartinez', 'mwilliams', '2014-02-23 7:05:00', 'You do not need to do anything else. You will be set to go for your surgery on the 25th.', 'r'"
	};
	
	private String[] docToDocMessages = {"'gwells', 'abloom', '2014-04-07 06:15:00', 'Are you currently treating Julianne Lovato?', 'r'",
			"'hwood', 'nmartell', '2014-04-11 09:24:00', 'Did patient Michelle Hall complain to you about any side effects with her current medications?', 'r'",
			"'smartinez', 'jmaine', '2014-02-14 7:09:00', 'Are you going to be in the office on Monday?', 'r'"	
	};

	
	/**
	 * Create all the tables needed in the database.
	 */
	public CreateTables() throws SQLException {
		connectDB();
		//insertDummyData(users, "user");
		//insertDummyData(patients, "patient");
		//insertDummyData(doctors, "doctor");
		//insertDummyData(admins, "admin");
		//insertDummyData(requestAppt, "request_appointment");
		//insertDummyData(visits, "visit");
		//insertDummyData(paymentInfo, "payment_information");
		//insertDummyData(prescriptions, "prescription");
		//insertDummyData(surgeries, "surgery");
		//insertDummyData(preopMeds, "preoperative_medications");
		//insertDummyData(performs, "performs");
		//insertDummyData(diagnoses, "diagnosis");
		//insertDummyData(messagesToDoc, "send_message_to_doc");
		//insertDummyData(messagesToPatient, "send_message_to_pat");
		//insertDummyData(docToDocMessages, "communicates_with");

		makeQuery();	
	}
	
	/**
	 * Connect to the database. 
	 */
	public void connectDB() {
		try {
			dbc = new DBConnector();
			conn = dbc.getConnection();	
		} catch (SQLException e) {
			System.out.println("Error: Connection to CS4400 Group 1 database failed!");
            e.printStackTrace();
        }
	}	
	
	/**
	 * Insert dummy data. 
	 * 
	 * @data the array containing dummy data
	 * @tableName name of the table where data needs to be stored
	 */
	public void insertDummyData(String[] data, String tableName) throws SQLException {
	    stmt = null;
	    try {
	        stmt = conn.createStatement();
	        for (int i = 0; i < data.length; i++) {
        		stmt.executeUpdate("insert into cs4400_Group_1." + tableName + " values(" + data[i] + ")");
	        }
	    } catch (SQLException e) {
	        System.out.println("Error: Could not create tables. Connection to database failed!");
	        e.printStackTrace();
	    } finally {
	        if (stmt != null) { stmt.close(); }
	    }	
	}	
	
	public void deleteAllTuples(String tableName) throws SQLException {
	    stmt = null;
	    try {
	        stmt = conn.createStatement();
        	stmt.executeUpdate("delete from " + tableName );
	    } catch (SQLException e) {
	        System.out.println("Error: Could not delete tuples from tables. Connection to database failed!");
	        e.printStackTrace();
	    } finally {
	        if (stmt != null) { stmt.close(); }
	    }		
	}
	
	public void makeQuery() throws SQLException {
		Statement stmt = null;
		String query = "select * " +
		               "from cs4400_Group_1.send_message_to_doc";
		try {
		    stmt = conn.createStatement();
		    ResultSet rs = stmt.executeQuery(query);
		    /*while (rs.next()) {
		        String a = rs.getString("dusername");
		        String b = rs.getString("license_number");
		        String c = rs.getString("first_name");
		        String d = rs.getString("last_name");
		        String e = rs.getString("dob");
		        String f= rs.getString("work_phone");
		        String g = rs.getString("specialty");
		        String h= rs.getString("room_number");
		        String i= rs.getString("home_address");
		        System.out.println(a + " " + b + " " + c + " " + d + " " + e + " " + f + " " + h + " " + i);
		    }*/
		    /*while (rs.next()) {
		    	String a = rs.getString("dusername");
		        String b = rs.getString("pusername");
		        String c = rs.getString("appt_date");
		        String d = rs.getString("appt_start");
		        String e = rs.getString("appt_end");		        
		    	System.out.println(a + " " + b + " " + c + " " + d + " " + e);
		    }*/
		    /*while (rs.next()) {
		    	String a = rs.getString("dusername");
		        String b = rs.getString("pusername");
		        String c = rs.getString("date_of_visit");
		        String d = rs.getString("billing_amount");
		        String e = rs.getString("diastolic_bp");		
		        String f = rs.getString("systolic_bp");
		    	System.out.println(a + " " + b + " " + c + " " + d + " " + e + " " + f);
		    }
		    while (rs.next()) {
		    	String a = rs.getString("card_number");
		        String b = rs.getString("holder_name");
		        String c = rs.getString("type");
		        String d = rs.getString("exp_date");
		        String e = rs.getString("cvv");		
		    	System.out.println(a + " " + b + " " + c + " " + d + " " + e);
		    }
		    while (rs.next()) {
		        String a = rs.getString("dusername");
		        String b = rs.getString("pusername");
		        String c = rs.getString("date_of_visit");
		        String d = rs.getString("med_name");
		        String e = rs.getString("dosage");
		        String f= rs.getString("duration");
		        String g = rs.getString("notes");
		        String h= rs.getString("is_ordered");
		        System.out.println(a + " " + b + " " + c + " " + d + " " + e + " " + f + " " + g + " " + h);
		    }
		    while (rs.next()) {
		        String a = rs.getString("dusername");
		        String b = rs.getString("pusername");
		        String c = rs.getString("cpt_code");
		        String d = rs.getString("num_assistants");
		        String e = rs.getString("s_start");
		        String f= rs.getString("s_end");
		        String g = rs.getString("a_start");
		        String h= rs.getString("complications");
		        System.out.println(a + " " + b + " " + c + " " + d + " " + e + " " + f + " " + g + " " + h);
		    }	
		    while (rs.next()) {
		        String a = rs.getString("cpt_code");
		        String b = rs.getString("surgery_type");
		        String c = rs.getString("cost");
		        System.out.println(a + " " + b + " " + c );
		    }
		    while (rs.next()) {
		        String a = rs.getString("dusername1");
		        String b = rs.getString("dusername2");
		        String c = rs.getString("date_time");
		        String d = rs.getString("content");
		        String e = rs.getString("status");
		        System.out.println(a + " " + b + " " + c + " " + d + " " + e);
		    }*/
		    while (rs.next()) {
		        String a = rs.getString("dusername");
		        String b = rs.getString("pusername");
		        String c = rs.getString("date_time");
		        String d = rs.getString("content");
		        String e = rs.getString("status");
		        System.out.println(a + " " + b + " " + c + " " + d + " " + e);
		    }
		} catch (SQLException e ) {
		    e.printStackTrace();
		} finally {
		    if (stmt != null) { stmt.close(); }
		}			
	}  
	
	public String insertUserStatement(String username, String password) {
		return "insert into cs4400_Group_1.USER values('" + username + "','" + password + "')";
	}
	
	public static void main(String[] args) throws SQLException {
		new CreateTables();
	}
}
