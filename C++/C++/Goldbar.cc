#include <iostream>
#include <sstream>
#include <cstdio>
#include <cstdlib>
#include <ctime>
#include <cmath>
#include <vector>
#include <algorithm>
using namespace std;

#define MIN(A, B) (A < B) ? A : B
#define MAX(A, B) (A > B) ? A : B

/**
 * chromosome: 0000000 (1 if in else 0)
 */
class Goldbar {
private:
	const static int N			= 100;
	const static int COUNT		= 7;
	const static int MAX_WEIGHT = 40;
	// string *chromosomes[N];
	vector<string*> chromosomes;
	int values[COUNT] = { 15, 8, 10, 9, 13, 7, 12 };
	int weights[COUNT] = { 12, 10, 8, 8, 12, 5, 8 };

	/**
	 * ONLY weights matters.
	 */
	bool is_valid(const string *str) {
		//cout << "[Function] is_valid(" << str << ") at " << std::time(0) << endl;
		if (str == nullptr || str->length() != COUNT) return false;
		return (get_value(str) <= MAX_WEIGHT);
	}

	string *random_chromosome() {
		int idx = std::rand() % (COUNT + 1);
		string *chromosome = new string("");
		for (int i = 0; i < COUNT; i++) {
			chromosome->append(to_string(i == idx));
		}
		return chromosome;
	}
	/*
	string *random_chromosome() {
		//cout << "[Function] random_chromosome() at " << std::time(0) << endl;
		string *chromosome = nullptr;
		while (!is_valid(chromosome)) {
			if (chromosome != nullptr) delete chromosome;
			chromosome = new string("");
			for (int i = 0; i < COUNT; i++) {
				chromosome->append(to_string(std::rand() % 2));
			}
		}
		//cout << "Random chromosome: " << chromosome << endl;
		return chromosome;
	}
	*/

	/**
	 * one[:cut] + other[cut:]
	 */
	string crossover(const string *one, const string *other) {
		string child;
		int cut = std::rand() % COUNT;
		/*
		while (!is_valid(&child)) {
			int cut = std::rand() % COUNT;
			// cout << "[Function] crossover :: cut: " << cut << endl;
			child = string(one->substr(0, cut) + other->substr(cut));
			// cout << "[Function] crossover :: child: " << child << endl;
		}
		//cout << "[Function] CROSSOVER: " << child << endl;
		*/
		child = string(one->substr(0, cut) + other->substr(cut));
		if (!is_valid(&child)) {
			string *temp = random_chromosome();
			child = *temp;
			delete temp;
		}
		return child;
	}

	void mutation(string *chromosome) {
		int idx = std::rand() % COUNT;
		string child = string(*chromosome);
		child[idx] = (child[idx] == '1') ? '0' : '1';
		if (is_valid(&child))
			*chromosome = child;
		// (*chromosome)[idx] = (((*chromosome)[idx] == '1') ? '0' : '1');
	}
public:
	Goldbar() {
		for (int i = 0; i < N; i++) {
			chromosomes.push_back(random_chromosome());
		}
	}
	~Goldbar() {
		for (int i = 0; i < chromosomes.size(); i++) {
		// for (vector<string*>::iterator itr = chromosomes.begin(); itr != chromosomes.end(); itr++) {
			delete chromosomes[i];
		}
	}

	int get_value(const string* chromosome) {
		int sum = 0;
		for (int i = 0; i < COUNT; i++) if ((*chromosome)[i] == '1') {
			sum += weights[i];
		}
		return sum;
	}

	void order_by_value() {
		cout << "[Function] order_by_value...";
		sort(chromosomes.begin(), chromosomes.end(), [this](const string* a, const string* b) {
			return get_value(a) > get_value(b);
		});
		cout << " done" << endl;
	}

	void print_chromosomes() {
		int value, sum = 0;
		int min = 40, max = 0;
		for (int i = 0; i < N; i++) {
			printf("[%3d]: ", i + 1);
			value = get_value(chromosomes[i]);
			sum += value;
			cout << *chromosomes[i] << " (value: " << value << ")" << endl;
			min = MIN(min, value);
			max = MAX(max, value);
		}
		cout << "Total: " << sum << ", Average: " << (double)sum / N << ", MAX: " << max << ", MIN: " << min << endl << endl;
	}

	void run(int generation = 100, double drop_rate = 0.3, double mutation_rate = 1/*%*/) {
		int endpoint = chromosomes.size() * (1 - drop_rate);
		cout << "First Generation ===" << endl;
		print_chromosomes();
		while (generation--) {
			order_by_value();
			cout << "[Function] crossover..";
			for (int i = endpoint; i < chromosomes.size(); i++) {
				//cout << "Crossover at chromosomes[" << i << "]: " << *chromosomes[i] << endl;
				chromosomes[i]->assign(crossover(chromosomes[i - endpoint], chromosomes[i - endpoint + 1]));
			}
			cout << " done" << endl;
			//cout << "Mutation check: ";
			for (int i = 0; i < N; i++) {
				cout << i << " ";
				if (std::rand() % 100 <= mutation_rate) {
					//cout << endl << "occur at " << i << endl;
					mutation(chromosomes[i]);
				}
			}
			//cout << endl;
		}
		order_by_value();
		cout << "Last Generation ===" << endl;
		print_chromosomes();
	}
};

int main()
{
	std::srand(static_cast<unsigned int>(std::time(0)));

	Goldbar *goldbar = new Goldbar();
	goldbar->run(10);

	delete goldbar;
	return 0;
}