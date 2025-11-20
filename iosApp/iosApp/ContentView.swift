import UIKit
import SwiftUI
import ComposeApp

// MARK: - Auth Views
struct LoginView: UIViewControllerRepresentable {
    let onNavigateToRegister: () -> Void
    let onLoginSuccess: () -> Void

    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.LoginViewController(
            onNavigateToRegister: onNavigateToRegister,
            onLoginSuccess: onLoginSuccess
        )
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct RegisterView: UIViewControllerRepresentable {
    let onNavigateToLogin: () -> Void
    let onRegisterSuccess: () -> Void

    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.RegisterViewController(
            onNavigateToLogin: onNavigateToLogin,
            onRegisterSuccess: onRegisterSuccess
        )
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

// MARK: - Main App Views
struct HomeView: UIViewControllerRepresentable {
    let viewModel: HomeViewModel

    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.HomeViewController(viewModel: viewModel)
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct SearchView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.SearchViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct SettingsView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.SettingsViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

// MARK: - Home View Model Wrapper
class HomeViewModelWrapper: ObservableObject {
    let viewModel = HomeViewModel()
    @Published var showMap: Bool = true

    func toggle() {
        viewModel.toggleView()
        showMap.toggle()
    }
}

// MARK: - Main Content View
struct ContentView: View {
    @State private var isAuthenticated = false
    @State private var showingRegister = false
    @StateObject var homeWrapper = HomeViewModelWrapper()

    var body: some View {
        if isAuthenticated {
            // Show main app with TabView
            TabView {
                NavigationStack {
                    HomeView(viewModel: homeWrapper.viewModel)
                        .ignoresSafeArea(edges: .all)
                        .navigationTitle("")
                        .navigationBarTitleDisplayMode(.inline)
                        .toolbar {
                            ToolbarItem(placement: .navigationBarTrailing) {
                                Button(action: {
                                    homeWrapper.toggle()
                                }) {
                                    Image(systemName: homeWrapper.showMap ? "list.bullet" : "map")
                                }
                            }
                        }
                }
                .tabItem {
                    Label("Inicio", systemImage: "house")
                }

                NavigationStack {
                    SearchView()
                }
                .tabItem {
                    Label("Buscar", systemImage: "magnifyingglass")
                }

                NavigationStack {
                    SettingsView()
                }
                .tabItem {
                    Label("Ajustes", systemImage: "gear")
                }
            }
        } else {
            // Show authentication flow
            if showingRegister {
                RegisterView(
                    onNavigateToLogin: {
                        showingRegister = false
                    },
                    onRegisterSuccess: {
                        isAuthenticated = true
                    }
                )
                .ignoresSafeArea()
            } else {
                LoginView(
                    onNavigateToRegister: {
                        showingRegister = true
                    },
                    onLoginSuccess: {
                        isAuthenticated = true
                    }
                )
                .ignoresSafeArea()
            }
        }
    }
}
